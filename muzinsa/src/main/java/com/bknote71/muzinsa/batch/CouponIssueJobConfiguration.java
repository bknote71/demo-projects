package com.bknote71.muzinsa.batch;

import com.bknote71.muzinsa.domain.coupon.DiscountPolicy;
import com.bknote71.muzinsa.domain.coupon.IssuedCoupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CouponIssueJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;

    @Value("${chunkSize:10}")
    private int chunkSize;

    @Bean
    public Job couponIssueJob() throws MalformedURLException {
        return jobBuilderFactory.get("couponIssueJob")
                .start(couponIssueStep())
                .build();
    }

    private Step couponIssueStep() throws MalformedURLException {
        return stepBuilderFactory.get("couponIssueStep")
                .<NewCouponDto, IssuedCoupon>chunk(chunkSize)
                .reader(couponReader(null))
                .processor(processor())
                .writer(couponJdbcWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<NewCouponDto> couponReader(@Value("#{jobParameters[requestDate]}") String requestDate) throws MalformedURLException {
        return new FlatFileItemReaderBuilder<NewCouponDto>()
                .name("couponReader")
                .resource(new ClassPathResource("coupon/" + requestDate + ".txt"))
                .delimited().delimiter(",")
                .names(new String[]{"brandId", "productId", "discountPolicy", "discount", "startDate", "endDate"})
                .targetType(NewCouponDto.class)
                .build();
    }

    private ItemProcessor<? super NewCouponDto, ? extends IssuedCoupon> processor() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dto -> IssuedCoupon.builder()
                .brandId(dto.getBrandId())
                .productId(dto.getProductId())
                .discountPolicy(DiscountPolicy.valueOf(dto.getDiscountPolicy()))
                .discount(dto.getDiscount())
                .startDate(LocalDate.parse(dto.getStartDate(), formatter).atStartOfDay())
                .endDate(LocalDate.parse(dto.getEndDate(), formatter).atStartOfDay())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<? super IssuedCoupon> couponJdbcWriter() {
        return new JdbcBatchItemWriterBuilder<IssuedCoupon>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(item -> {
                    Map<String, Object> params = new HashMap<>();
                    params.put("brandId", item.getBrandId());
                    params.put("productId", item.getProductId());
                    params.put("discountPolicy", item.getDiscountPolicy().name());
                    params.put("discount", item.getDiscount());
                    params.put("startDate", item.getStartDate());
                    params.put("endDate", item.getEndDate());
                    return new MapSqlParameterSource(params);
                })
                .sql("insert into issued_coupon(brand_id, product_id, discount_policy, discount, start_date, end_date) " +
                        "values (:brandId, :productId, :discountPolicy, :discount, :startDate, :endDate)")
                .build();
    }

//    @Bean
//    public JpaItemWriter<? super IssuedCoupon> couponWriter() {
//        return new JpaItemWriterBuilder<IssuedCoupon>()
//                .entityManagerFactory(emf)
//                .build();
//
//    }
}

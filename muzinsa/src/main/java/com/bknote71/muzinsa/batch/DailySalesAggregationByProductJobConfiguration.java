package com.bknote71.muzinsa.batch;

import com.bknote71.muzinsa.domain.history.ProductSalesHistory;
import com.bknote71.muzinsa.domain.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DailySalesAggregationByProductJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    private final EntityManagerFactory emf;

    @Value("${chunkSize:10}")
    private int chunkSize;

    @Bean
    public Job dailyAggregationJob() {
        return jobBuilderFactory.get("dailyAggregationByProductJob")
                .start(dailySalesHistory())
                .build();
    }

    @Bean
    @JobScope
    public Step dailySalesHistory() {
        return stepBuilderFactory.get("dailAggregationStep")
                .<Order, ProductSalesHistory>chunk(chunkSize)
                .reader(reader(null))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @StepScope
    private ItemReader<? extends Order> reader(@Value("#{jobParameters[requestDate]}") String requestDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<String, Object> params = new HashMap<>();
        LocalDateTime startDate = LocalDate.parse(requestDate, formatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(requestDate, formatter).atTime(LocalTime.MAX);

        System.out.println(startDate);
        System.out.println(endDate);

        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return new JpaPagingItemReaderBuilder<Order>()
                .name("reader")
                .entityManagerFactory(emf)
                .pageSize(chunkSize)
                .queryString("select o from Order o where o.createdAt between :startDate And :endDate")
                .parameterValues(params)
                .build();
    }


    /**
     * select op.productId, sum(op.quantity), sum(op.price)  from OrderProduct op
     * where op.order.createdAt between startDate and endDate groupBy op.productId
     * join Order o on o.orderStatus = DELIVERY_COMPLETED
     */

    private ItemProcessor<? super Order, ? extends ProductSalesHistory> processor() {
        return null;
    }


    private ItemWriter<? super ProductSalesHistory> writer() {
        return null;
    }
}

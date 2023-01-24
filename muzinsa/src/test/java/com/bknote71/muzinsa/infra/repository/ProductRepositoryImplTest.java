package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.domain.product.ProductOption;
import com.bknote71.muzinsa.domain.repository.ProductRepository;
import com.bknote71.muzinsa.infra.repository.impl.ProductRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ProductRepositoryImplTest {

    @Autowired ProductRepositoryImpl productRepositoryImpl;
    @Autowired ProductRepository productRepository;

    @Autowired EntityManager em;

    @Test
    void same_bean_Test() throws NoSuchFieldException, IllegalAccessException {
        Assertions.assertThat(productRepository).isEqualTo(productRepositoryImpl);
    }

    @Transactional
    @Test
    void save_product_test() {
        List<String> options1 = Arrays.asList("200", "210", "220");
        List<String> options2 = Arrays.asList("빨강", "파랑", "초록");
        List<ProductOption> list = new ArrayList<>();

        list.add(new ProductOption(options1));
        list.add(new ProductOption(options2));

        Product mp3 = Product.builder()
                .name("mp3")
                .price(3000)
                .stock(10)
                .options(list)
                .build();

        em.flush();
        em.clear();

        Product save = productRepository.save(mp3);

        Assertions.assertThat(mp3).isEqualTo(save);

        for (ProductOption option : mp3.getOptions()) {
            System.out.println("option: " + option.toString());
        }
    }
}
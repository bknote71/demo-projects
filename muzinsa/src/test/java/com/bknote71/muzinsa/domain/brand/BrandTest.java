package com.bknote71.muzinsa.domain.brand;

import com.bknote71.muzinsa.infra.repository.BrandJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandTest {

    @Autowired BrandJpaRepository brandRepository;

    int brandSize = 1000000;
    @Test
    public void init() {
        for (int i = 0; i < brandSize; ++i) {
            Brand brand = Brand.builder()
                    .brandname("무신사" + i)
                    .categoryId((long) i)
                    .companyname("무신사" + i)
                    .build();
            brandRepository.save(brand);
        }
    }

    @Test
    public void indexOffTest() {
        for (int i = 0; i < brandSize; ++i) {
            Brand brand = brandRepository.findByCompanyname("무신사" + i).get();
        }
    }

    @Test
    public void indexOnTestByBrandname() {
        for (int i = 0; i < brandSize; ++i) {
            Brand brand = brandRepository.findByBrandname("무신사" + i).get();
        }
    }
    
}
package com.bknote71.muzinsa.domain.coupon;

import com.bknote71.muzinsa.domain.repository.IssuedCouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CouponTest {

    @Autowired IssuedCouponRepository repository;
    @Autowired EntityManager em;

    @Test
    void discountpolicy_save() {
        DiscountPolicy discountPolicy = DiscountPolicy.AMOUNT;

        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .discountPolicy(discountPolicy)
                .discount(1000)
                .build();

        repository.save(issuedCoupon);

        em.flush();
        em.clear();

        IssuedCoupon c = repository.findById(1L).get();
        Assertions.assertThat(c.getDiscountPolicy().toString()).isEqualTo("AMOUNT");
    }

    @Test
    void amount_discountpolicy() {
        DiscountPolicy discountPolicy = DiscountPolicy.AMOUNT;

        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .discountPolicy(discountPolicy)
                .discount(1000)
                .build();

        IssuedCoupon save = repository.save(issuedCoupon);
        Long id = save.getId();

        em.flush();
        em.clear();

        IssuedCoupon c = repository.findById(id).get();

        int amount = c.calculateDiscountAmount(10000);
        Assertions.assertThat(amount).isEqualTo(1000);
    }

    @Test
    void percent_discountpolicy() {
        DiscountPolicy policy = DiscountPolicy.PERCENT;

        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .discountPolicy(policy)
                .discount(10) // 10% 할인
                .build();

        IssuedCoupon save = repository.save(issuedCoupon);
        Long id = save.getId();

        em.flush();
        em.clear();

        IssuedCoupon c = repository.findById(id).get();

        int amount = c.calculateDiscountAmount(1000);
        Assertions.assertThat(amount).isEqualTo(100);

    }

}
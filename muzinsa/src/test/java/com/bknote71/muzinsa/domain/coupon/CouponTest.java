package com.bknote71.muzinsa.domain.coupon;

import com.bknote71.muzinsa.domain.repository.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
@SpringBootTest
class CouponTest {

    @Autowired CouponRepository repository;
    @Autowired EntityManager em;

    @Test
    void discountpolicy_save() {
        DiscountPolicy discountPolicy = DiscountPolicy.AMOUNT;

        Coupon coupon = Coupon.builder()
                .discountPolicy(discountPolicy)
                .discount(1000)
                .build();

        repository.save(coupon);

        em.flush();
        em.clear();

        Coupon c = repository.findById(1L).get();
        Assertions.assertThat(c.getDiscountPolicy().toString()).isEqualTo("AMOUNT");
    }

    @Test
    void amount_discountpolicy() {
        DiscountPolicy discountPolicy = DiscountPolicy.AMOUNT;

        Coupon coupon = Coupon.builder()
                .discountPolicy(discountPolicy)
                .discount(1000)
                .build();

        Coupon save = repository.save(coupon);
        Long id = save.getId();

        em.flush();
        em.clear();

        Coupon c = repository.findById(id).get();

        int amount = c.calculateDiscountAmount(10000);
        Assertions.assertThat(amount).isEqualTo(1000);
    }

    @Test
    void percent_discountpolicy() {
        DiscountPolicy policy = DiscountPolicy.PERCENT;

        Coupon coupon = Coupon.builder()
                .discountPolicy(policy)
                .discount(10) // 10% 할인
                .build();

        Coupon save = repository.save(coupon);
        Long id = save.getId();

        em.flush();
        em.clear();

        Coupon c = repository.findById(id).get();

        int amount = c.calculateDiscountAmount(1000);
        Assertions.assertThat(amount).isEqualTo(100);

    }

}
package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.coupon.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueCouponJpaRepository extends JpaRepository<IssuedCoupon, Long> {
}

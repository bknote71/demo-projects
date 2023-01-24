package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
}

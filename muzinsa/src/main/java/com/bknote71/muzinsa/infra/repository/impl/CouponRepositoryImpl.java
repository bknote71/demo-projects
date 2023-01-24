package com.bknote71.muzinsa.infra.repository.impl;

import com.bknote71.muzinsa.domain.coupon.Coupon;
import com.bknote71.muzinsa.domain.repository.CouponRepository;
import com.bknote71.muzinsa.infra.repository.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository repository;

    @Override public Coupon save(Coupon coupon) {
        return repository.save(coupon);
    }

    @Override public Optional<Coupon> findById(Long id) {
        return repository.findById(id);
    }
}

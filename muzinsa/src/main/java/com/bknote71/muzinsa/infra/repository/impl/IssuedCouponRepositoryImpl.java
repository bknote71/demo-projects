package com.bknote71.muzinsa.infra.repository.impl;

import com.bknote71.muzinsa.domain.coupon.IssuedCoupon;
import com.bknote71.muzinsa.domain.repository.IssuedCouponRepository;
import com.bknote71.muzinsa.infra.repository.IssueCouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class IssuedCouponRepositoryImpl implements IssuedCouponRepository {

    private final IssueCouponJpaRepository repository;

    @Override public IssuedCoupon save(IssuedCoupon coupon) {
        return repository.save(coupon);
    }

    @Override public Optional<IssuedCoupon> findById(Long id) {
        return repository.findById(id);
    }
}

package com.bknote71.muzinsa.domain.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coupon {

    @Id @GeneratedValue
    @Column(name = "COUPON_ID")
    private Long id;

    private String name;
    private int discount;
    private boolean used;
    private Long accountId;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Embedded
    private CouponTarget target;
    public void use() {
        this.used = true;
    }

    public boolean used() {
        return used;
    }

    // todo: 1. 적용 가능 여부, 2. 할인 금액 계산 << 안에는 여러거자 조건 판단.
    public boolean isApplicable(Long brandId, Long productId, LocalDateTime now) {
        // 적용 대상 체크를 해야 한다
        // 적용 대상 1. 브랜드, 2. 단일 상품
        boolean isApplicable = target.isApplicable(brandId, productId);

        // 2. 날짜확인 << 임시로
        boolean between = isBetween(now);
        return isApplicable && between;
    }

    private boolean isBetween(LocalDateTime now) {
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    // 2.
    public int calculateDiscountAmount(int origin) {
        return discountPolicy.calculateDiscountAmount(origin, discount);
    }
}

package com.bknote71.muzinsa.domain.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IssuedCoupon {

    @Id @GeneratedValue
    @Column(name = "ISSUED_COUPON_ID")
    private Long id;

    private String name;
    private int discount;
    private boolean used;
    private Long accountId;
    private Long productId;
    private Long brandId;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public void use() {
        this.used = true;
    }

    public boolean used() {
        return used;
    }

    // todo: 1. 적용 가능 여부, 2. 할인 금액 계산 << 안에는 여러거자 조건 판단.
    public boolean isApplicable(LocalDateTime now) {
        // 쿠폰이 특정 상품에 적용 가능한지 >> ManyToMany

        // 2. 날짜확인 << 임시로
        boolean between = isBetween(now);
        return between;
    }

    private boolean isBetween(LocalDateTime now) {
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    // 2.
    public int calculateDiscountAmount(int origin) {
        return discountPolicy.calculateDiscountAmount(origin, discount);
    }
}

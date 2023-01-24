package com.bknote71.muzinsa.domain.coupon;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
public class Coupon {

    @Id @GeneratedValue
    @Column(name = "COUPON_ID")
    private Long id;

    private String name;

    // discount 유형 및 적용 대상(매니투매니)

}

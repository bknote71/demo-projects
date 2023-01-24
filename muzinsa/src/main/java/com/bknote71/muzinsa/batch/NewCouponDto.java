package com.bknote71.muzinsa.batch;

import lombok.Data;

@Data
public class NewCouponDto {
    private Long brandId;
    private Long productId;
    private String discountPolicy;
    private int discount;
    private String startDate;
    private String endDate;

    @Override public String toString() {
        return "NewCouponDto{" +
                "brandId='" + brandId + '\'' +
                ", productId='" + productId + '\'' +
                ", discountType='" + discountPolicy + '\'' +
                ", discount='" + discount + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

package com.bknote71.muzinsa.batch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewCouponDto {
    private String discountPolicy;
    private int discount;
    private String startDate;
    private String endDate;
    private String targetType;
    private List<Long> ids;

    @Override public String toString() {
        return "NewCouponDto{" +
                "discountPolicy='" + discountPolicy + '\'' +
                ", discount=" + discount +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", targetType='" + targetType + '\'' +
                ", ids=" + ids +
                '}';
    }
}

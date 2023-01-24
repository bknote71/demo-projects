package com.bknote71.muzinsa.domain.service;

import com.bknote71.muzinsa.domain.coupon.Coupon;
import com.bknote71.muzinsa.domain.order.Order;

import java.util.Map;

public interface OrderPaymentService {

    // 지불 >> 총액 계산: 가격, 배달 가격, 할인 가격을 합친 총 가격을 계산한다.
    int pay(Order order, Map<Long, Coupon> couponMap);
}

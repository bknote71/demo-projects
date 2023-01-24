package com.bknote71.muzinsa.infra.serviceimpl;

import com.bknote71.muzinsa.domain.coupon.Coupon;
import com.bknote71.muzinsa.domain.order.Order;
import com.bknote71.muzinsa.domain.service.OrderPaymentService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {
    // 지불 >> 총액 계산: 가격, 배달 가격, 할인 가격을 합친 총 가격을 계산한다.
    @Override public int pay(Order order, Map<Long, Coupon> couponMap) {
        int finalAmount = order.getOrderProducts().stream()
                .mapToInt(op -> op.getDeliveryfee() +
                        discountAmountByCoupon(op.getPrice() * op.getQuantity(), couponMap.get(op.getId())))
                .sum();

        return finalAmount;
    }

    private int discountAmountByCoupon(int amount, Coupon coupon) {
        if (coupon == null) {
            return amount;
        }
        // 할인 로직
        int discountAmount = coupon.calculateDiscountAmount(amount);
        coupon.use();
        return amount - discountAmount;
    }
}

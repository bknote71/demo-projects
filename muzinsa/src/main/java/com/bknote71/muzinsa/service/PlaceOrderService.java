package com.bknote71.muzinsa.service;

import com.bknote71.muzinsa.domain.coupon.Coupon;
import com.bknote71.muzinsa.domain.order.*;
import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.domain.repository.CouponRepository;
import com.bknote71.muzinsa.domain.repository.OrderProductRepository;
import com.bknote71.muzinsa.domain.repository.OrderRepository;
import com.bknote71.muzinsa.domain.repository.ProductRepository;
import com.bknote71.muzinsa.domain.service.OrderPaymentService;
import com.bknote71.muzinsa.dto.order.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PlaceOrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    private final OrderPaymentService orderPaymentService;

    @Transactional
    public void placeOrder(DeliveryInfo deliveryInfo, List<OrderProductDto> orderProductDtos, int paidAmount, Long accountId) {
        Order order = createOrder(orderProductDtos, deliveryInfo, accountId);
        // 쿠폰 검색 <<
        Map<Long, Coupon> couponMap = validateIfCouponExistAndReturnMap(order.getOrderProducts());
        // 지불 >> 총액 계산: 가격, 배달 가격, 할인 가격을 합친 총 가격을 계산한다.
        int finalAmount = orderPaymentService.pay(order, couponMap);
        if (finalAmount != paidAmount) // 지불 금액과 계산한 금액이 동일한지 체크
            throw new IllegalArgumentException("지불 금액이 맞지 않슴니다." + finalAmount + " 만큼 내야 합니다.");
        // processUsedIssuedCoupon(couponMap); // 사용한 쿠폰 처리
    }

    private Order createOrder(List<OrderProductDto> orderProductDtos, DeliveryInfo deliveryInfo, Long accountId) {
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderProductDto dto : orderProductDtos) {
            validateOrderProduct(dto);
            OrderProduct orderProduct = orderProductRepository.findById(dto.getOrderProductId()).get();
            orderProduct.setCouponId(dto.getCouponId());
            orderProducts.add(orderProduct);
        }

        Order createdOrder = Order.builder()
                .orderer(new Orderer(accountId))
                .orderProducts(orderProducts)
                .orderStatus(OrderStatus.PAYMENT_WAITING)
                .deliveryInfo(deliveryInfo)
                .build();

        return orderRepository.save(createdOrder);
    }

    private void validateOrderProduct(OrderProductDto orderProductDto) {
        // 1. null, 2. 등록된 상품이어야 함, 3. 판매중, 4. 수량이 0 보다 커야함
        Long productId = orderProductDto.getProductId();
        if (productId == null) {
            throw new IllegalArgumentException("product is null");
        }
        // 논리적인 검사
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException(productId + " 는 등록되지 않은 상품입니다."));

        findProduct.verifyIfYouCanBuyThis(orderProductDto.getQuantity());
    }

    private Map<Long, Coupon> validateIfCouponExistAndReturnMap(List<OrderProduct> orderProducts) {
        Map<Long, Coupon> result = new HashMap<>();
        orderProducts.stream().forEach(op -> {
            Long cid = op.getCouponId();
            if (cid != null) {
                Coupon coupon = couponRepository.findById(cid)
                        .orElseThrow(() -> new IllegalArgumentException(cid + " 는 등록되지 않은 쿠폰입니다."));
                // 쿠폰을 orderProduct에 적용이 가능한지 확인.

                //
                result.put(op.getId(), coupon);
            }
        });
        return result;
    }

    private void processUsedIssuedCoupon(Map<Long, Coupon> couponMap) {
        for (Map.Entry<Long, Coupon> entry : couponMap.entrySet()) {
            Coupon coupon = entry.getValue();
            coupon.use();
        }
    }
}

package com.bknote71.muzinsa.service;

import com.bknote71.muzinsa.domain.coupon.IssuedCoupon;
import com.bknote71.muzinsa.domain.order.DeliveryInfo;
import com.bknote71.muzinsa.domain.order.Order;
import com.bknote71.muzinsa.domain.order.OrderProduct;
import com.bknote71.muzinsa.domain.order.OrderStatus;
import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.domain.repository.IssuedCouponRepository;
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
    private final ProductRepository productRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    private final OrderPaymentService orderPaymentService;

    @Transactional
    public void placeOrder(DeliveryInfo deliveryInfo, List<OrderProductDto> orderProductDtos, int paidAmount) {
        Order order = createOrder(orderProductDtos, deliveryInfo);
        // 쿠폰 검색 <<
        Map<Long, IssuedCoupon> couponMap = validateIfCouponExistAndReturnMap(order.getOrderProducts());
        // 지불 >> 총액 계산: 가격, 배달 가격, 할인 가격을 합친 총 가격을 계산한다.
        int finalAmount = orderPaymentService.pay(order, couponMap);
        if (finalAmount != paidAmount) // 지불 금액과 계산한 금액이 동일한지 체크
            throw new IllegalArgumentException("지불 금액이 맞지 않슴니다." + finalAmount + " 만큼 내야 합니다.");
        processUsedIssuedCoupon(couponMap); // 사용한 쿠폰 처리
    }

    private Order createOrder(List<OrderProductDto> orderProductDtos, DeliveryInfo deliveryInfo) {
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderProductDto dto : orderProductDtos) {
            validOrderLine(dto);
            orderProducts.add(new OrderProduct(
                    dto.getProductId(),
                    dto.getCouponId(),
                    dto.getPrice(),
                    dto.getQuantity(),
                    dto.getDeliveryfee(), // delivery 요금 계산은 숍에 따라 다르기 때문에 나중에 계산 << 지금은 그냥 넣기
                    dto.getOrderOptionListDto().toOrderOptionList()));
        }

        Order createdOrder = Order.builder()
                .orderProducts(orderProducts)
                .orderStatus(OrderStatus.PAYMENT_WAITING)
                .deliveryInfo(deliveryInfo)
                .build();

        return orderRepository.save(createdOrder);
    }

    private void validOrderLine(OrderProductDto orderProductDto) {
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

    private Map<Long, IssuedCoupon> validateIfCouponExistAndReturnMap(List<OrderProduct> orderProducts) {
        Map<Long, IssuedCoupon> result = new HashMap<>();
        orderProducts.stream().forEach(op -> {
            Long cid = op.getCouponId();
            if (cid != null) {
                IssuedCoupon issuedCoupon = issuedCouponRepository.findById(cid)
                        .orElseThrow(() -> new IllegalArgumentException(cid + " 는 등록되지 않은 쿠폰입니다."));
                result.put(op.getId(), issuedCoupon);
            }
        });
        return result;
    }

    private void processUsedIssuedCoupon(Map<Long, IssuedCoupon> couponMap) {
        for (Map.Entry<Long, IssuedCoupon> entry : couponMap.entrySet()) {
            IssuedCoupon issuedCoupon = entry.getValue();
            issuedCoupon.use();
        }
    }
}

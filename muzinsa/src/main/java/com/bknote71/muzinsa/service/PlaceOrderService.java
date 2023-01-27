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
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    private ThreadLocal<Map<Long, Coupon>> couponMap = ThreadLocal.withInitial(HashMap::new);

    @Transactional
    public Order placeOrder(DeliveryInfo deliveryInfo, List<OrderProductDto> orderProductDtos, int paidPrice, Long accountId) {
        Assert.notNull(orderProductDtos, "주문할 수 있는 상품이 없습니다");
        Assert.notNull(deliveryInfo, "주문 주소가 없습니다.");

        Order order = createOrder(orderProductDtos, deliveryInfo, accountId);
        // 지불 >> 총액 계산: 가격, 배달 가격, 할인 가격을 합친 총 가격을 계산한다.
        int orderPrice = orderPaymentService.pay(order, getCouponMap());
        if (orderPrice != paidPrice) // 지불 금액과 계산한 금액이 동일한지 체크
            throw new IllegalArgumentException("지불 금액이 맞지 않슴니다." + orderPrice + " 만큼 내야 합니다.");
        order.setOrderPrice(orderPrice);
        removeCouponMap();
        return order;
    }

    private Order createOrder(List<OrderProductDto> orderProductDtos, DeliveryInfo deliveryInfo, Long accountId) {
        List<OrderProduct> orderProducts = orderProductDtos.stream()
                .map(opDto -> {
                    OrderProduct op = getOrderProduct(opDto);
                    Product product = validateProduct(opDto.getProductId(), opDto.getQuantity());
                    if (opDto.getCouponId() != null) {
                        validateCoupon(op, opDto.getCouponId(), product);
                    }
                    return op;
                }).toList();

        Order createdOrder = Order.builder()
                .orderer(new Orderer(accountId))
                .orderProducts(orderProducts)
                .orderStatus(OrderStatus.PAYMENT_WAITING)
                .deliveryInfo(deliveryInfo)
                .build();

        return orderRepository.save(createdOrder);
    }

    private OrderProduct getOrderProduct(OrderProductDto dto) {
        return orderProductRepository.findById(dto.getOrderProductId()).get();
    }

    private Product validateProduct(Long productId, int quantity) {
        // 1. null, 2. 등록된 상품이어야 함, 3. 판매중, 4. 수량이 0 보다 커야함
        if (productId == null) {
            throw new IllegalArgumentException("product is null");
        }
        // 논리적인 검사
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException(productId + " 는 등록되지 않은 상품입니다."));

        findProduct.buy(quantity);
        return findProduct;
    }

    private void validateCoupon(OrderProduct op, Long cid, Product product) {
        Coupon coupon = couponRepository.findById(cid)
                .orElseThrow(() -> new IllegalArgumentException(cid + " 는 등록되지 않은 쿠폰입니다."));
        if (!coupon.isApplicable(product.getBrandId(), product.getId(), LocalDateTime.now())) {
            throw new IllegalArgumentException("잘못된 쿠폰입니다.");
        }
        op.setCouponId(cid);
        getCouponMap().put(op.getId(), coupon);
    }

    private Map<Long, Coupon> getCouponMap() {
        return couponMap.get();
    }

    private void removeCouponMap() {
        couponMap.remove();
    }
}

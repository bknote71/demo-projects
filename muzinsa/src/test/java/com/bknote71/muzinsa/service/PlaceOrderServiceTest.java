package com.bknote71.muzinsa.service;

import com.bknote71.muzinsa.domain.Address;
import com.bknote71.muzinsa.domain.coupon.Coupon;
import com.bknote71.muzinsa.domain.coupon.CouponTarget;
import com.bknote71.muzinsa.domain.coupon.DiscountPolicy;
import com.bknote71.muzinsa.domain.coupon.TargetType;
import com.bknote71.muzinsa.domain.order.DeliveryInfo;
import com.bknote71.muzinsa.domain.order.Order;
import com.bknote71.muzinsa.domain.order.OrderProduct;
import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.domain.product.ProductStatus;
import com.bknote71.muzinsa.domain.repository.CouponRepository;
import com.bknote71.muzinsa.domain.repository.OrderProductRepository;
import com.bknote71.muzinsa.domain.repository.OrderRepository;
import com.bknote71.muzinsa.domain.repository.ProductRepository;
import com.bknote71.muzinsa.domain.service.OrderPaymentService;
import com.bknote71.muzinsa.dto.order.OrderProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class PlaceOrderServiceTest {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderProductRepository orderProductRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CouponRepository couponRepository;
    @Autowired private OrderPaymentService orderPaymentService;

    @Autowired private PlaceOrderService placeOrderService;

    @Test
    void placeOrderTest() {
        Product p1 = new Product(1L, 1L, "운동화", 1000, 10, "운동화", null, ProductStatus.ON_SALE);
        Product p2 = new Product(2L, 2L, "운동화2", 10000, 10, "운동화", null, ProductStatus.ON_SALE);

        OrderProduct op1 = new OrderProduct(1L, null, 1000, 2, 0, null);
        OrderProduct op2 = new OrderProduct(2L, null, 10000, 2, 0, null);

        LocalDateTime sdate = LocalDateTime.now();
        LocalDateTime edate = LocalDateTime.now().plus(20, ChronoUnit.DAYS);
        Coupon c1 = new Coupon(1L, null, 1000, false, null, DiscountPolicy.AMOUNT, sdate, edate, new CouponTarget(TargetType.PRODUCT, Arrays.asList(1L, 2L)));

        productRepository.save(p1);
        productRepository.save(p2);
        OrderProduct sop = orderProductRepository.save(op1);
        OrderProduct sop2 = orderProductRepository.save(op2);
        Coupon sc = couponRepository.save(c1);

        DeliveryInfo dinfo = new DeliveryInfo(new Address("0123", "제주시 정실 5길 31-2", "단독"), "본인", "01039807629", "01039807629", "없음");
        OrderProductDto d1 = new OrderProductDto(sop.getId(), 1L, null, 1000, 2, 0, 0, sc.getId());
        OrderProductDto d2 = new OrderProductDto(sop2.getId(), 2L, null, 10000, 2, 0, 0, sc.getId());
        List<OrderProductDto> opList = Arrays.asList(d1, d2);
        Order order = placeOrderService.placeOrder(dinfo, opList, 20000, null);

        Assertions.assertThat(order.getOrderPrice()).isEqualTo(20000);
    }
}
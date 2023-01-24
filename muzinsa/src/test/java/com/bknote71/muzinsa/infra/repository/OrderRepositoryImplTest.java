package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.order.*;
import com.bknote71.muzinsa.domain.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OrderRepositoryImplTest {

    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em;

    @Transactional
    @Test
    void orderWithOrderProduct() {
        List<OrderOption> orderOptions = Arrays.asList(new OrderOption("220"), new OrderOption("파랑"));
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(null, null, 1000, 10, 2000, orderOptions));
        orderProducts.add(new OrderProduct(null, null, 10000, 5, 0, orderOptions));
        orderProducts.add(new OrderProduct(null, null, 50000, 20, 0, orderOptions));

        Order order = Order.builder()
                .orderProducts(orderProducts)
                .orderStatus(OrderStatus.PAYMENT_WAITING)
                .build();

        Order savedOrder = orderRepository.save(order);

        em.flush();
        em.clear();

        Order order1 = orderRepository.findById(savedOrder.getId()).get();
        Assertions.assertThat(order1.getId()).isEqualTo(savedOrder.getId());
        Assertions.assertThat(order1.calculateTotalPrice()).isEqualTo(10000 + 50000 + 1000000);
    }
}
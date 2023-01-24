package com.bknote71.muzinsa.infra.repository.impl;

import com.bknote71.muzinsa.domain.order.Order;
import com.bknote71.muzinsa.domain.repository.OrderRepository;
import com.bknote71.muzinsa.infra.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository repository;

    @Override public Order save(Order order) {
        return repository.save(order);
    }

    @Override public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }
}

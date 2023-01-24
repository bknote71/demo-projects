package com.bknote71.muzinsa.infra.repository.impl;

import com.bknote71.muzinsa.domain.order.OrderProduct;
import com.bknote71.muzinsa.domain.repository.OrderProductRepository;
import com.bknote71.muzinsa.dto.order.OrderProductDto;
import com.bknote71.muzinsa.infra.repository.OrderProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OrderProductRepositoryImpl implements OrderProductRepository {

    private final OrderProductJpaRepository repository;

    @Override public OrderProduct save(OrderProduct orderProduct) {
        return repository.save(orderProduct);
    }

    @Override public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override public Optional<OrderProduct> findById(Long id) {
        return repository.findById(id);
    }
}

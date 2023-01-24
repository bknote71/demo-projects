package com.bknote71.muzinsa.domain.repository;

import com.bknote71.muzinsa.domain.order.OrderProduct;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository {
    OrderProduct save(OrderProduct orderProduct);

    void delete(Long id);

    Optional<OrderProduct> findById(Long id);
}

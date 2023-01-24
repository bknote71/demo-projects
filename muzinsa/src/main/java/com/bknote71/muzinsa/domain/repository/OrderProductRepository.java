package com.bknote71.muzinsa.domain.repository;

import com.bknote71.muzinsa.domain.order.OrderProduct;

public interface OrderProductRepository {
    OrderProduct save(OrderProduct orderProduct);

    void delete(Long orderProductId);
}

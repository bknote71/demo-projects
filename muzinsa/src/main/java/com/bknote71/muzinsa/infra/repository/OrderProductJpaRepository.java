package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {
}

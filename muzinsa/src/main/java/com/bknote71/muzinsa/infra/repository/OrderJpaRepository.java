package com.bknote71.muzinsa.infra.repository;


import com.bknote71.muzinsa.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}

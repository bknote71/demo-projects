package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}

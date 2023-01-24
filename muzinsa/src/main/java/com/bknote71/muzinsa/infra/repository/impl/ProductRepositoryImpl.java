package com.bknote71.muzinsa.infra.repository.impl;

import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.domain.repository.ProductRepository;
import com.bknote71.muzinsa.infra.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository repository;

    @Override public Product save(Product product) {
        return repository.save(product);
    }

    @Override public Optional<Product> findById(Long id) {
        System.out.println("call findById");
        return repository.findById(id);
    }

    @Override public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

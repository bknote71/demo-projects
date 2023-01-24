package com.bknote71.muzinsa.infra.repository.impl;

import com.bknote71.muzinsa.domain.brand.Brand;
import com.bknote71.muzinsa.domain.repository.BrandRepository;
import com.bknote71.muzinsa.infra.repository.BrandJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BrandRepositoryImpl implements BrandRepository {
    private final BrandJpaRepository repository;

    @Override public Brand save(Brand brand) {
        return repository.save(brand);
    }

    @Override public Optional<Brand> findById(Long id) {
        return repository.findById(id);
    }

    @Override public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override public Optional<Brand> findByName(String name) {
        return repository.findByName(name);
    }
}

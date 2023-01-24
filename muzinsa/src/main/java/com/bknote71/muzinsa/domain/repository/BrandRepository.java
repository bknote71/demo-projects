package com.bknote71.muzinsa.domain.repository;

import com.bknote71.muzinsa.domain.brand.Brand;

import java.util.Optional;

public interface BrandRepository extends GenericRepository<Brand> {
    Optional<Brand> findByName(String name);
}

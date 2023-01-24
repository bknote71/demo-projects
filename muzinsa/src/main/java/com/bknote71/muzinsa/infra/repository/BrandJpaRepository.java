package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandJpaRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
}

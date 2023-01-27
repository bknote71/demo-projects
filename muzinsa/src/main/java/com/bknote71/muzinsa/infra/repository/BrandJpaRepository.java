package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandJpaRepository extends JpaRepository<Brand, Long> {

    @Query("select b from Brand b where b.brandname = :brandname")
    Optional<Brand> findByBrandname(@Param("brandname") String brandname);

    Optional<Brand> findByCategoryId(Long categoryId);

    Optional<Brand> findByCompanyname(String companyname);
}

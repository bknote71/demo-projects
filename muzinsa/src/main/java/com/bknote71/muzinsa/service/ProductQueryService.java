package com.bknote71.muzinsa.service;

import com.bknote71.muzinsa.controller.RestPage;
import com.bknote71.muzinsa.domain.brand.Brand;
import com.bknote71.muzinsa.domain.repository.BrandRepository;
import com.bknote71.muzinsa.dto.condition.ProductSearchCondition;
import com.bknote71.muzinsa.dto.product.ProductDto;
import com.bknote71.muzinsa.infra.repository.impl.ProductQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional()
@Service
public class ProductQueryService {

    private final ProductQueryRepositoryImpl productRepository;
    private final BrandRepository brandRepository;

    @Cacheable(value = "productList", key = "'pageable' + #pageable.getPageNumber() + ';' + #pageable.getPageSize()")
    public Page<ProductDto> getProductList(Pageable pageable) {
        return new RestPage<>(productRepository.findAll(pageable)
                .map(ProductDto::new));
    }

    @Cacheable(value = "searchProductList", keyGenerator = "productSearchConditionKeyGenerator")
    public Page<ProductDto> searchProductList(ProductSearchCondition cond, Pageable pageable) {
        if (StringUtils.hasText(cond.getBrand())) {
            Brand brand = brandRepository.findByName(cond.getBrand())
                    .orElseThrow(() -> new IllegalArgumentException("존재하는 브랜드명이 아닙니다."));
            cond.setBrandId(brand.getId());
        }
        return new RestPage<>(productRepository.findBySearchCondition(cond, pageable)
                .map(ProductDto::new));
    }

    public void searchProductListByCategory(Long categoryId, Pageable pageable) {

    }
}

package com.bknote71.muzinsa.controller;

import com.bknote71.muzinsa.dto.condition.ProductSearchCondition;
import com.bknote71.muzinsa.dto.product.ProductDto;
import com.bknote71.muzinsa.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductQueryService query;

    @GetMapping("/list")
    public Page<ProductDto> list(Pageable pageable) {
        return query.getProductList(pageable);
    }

    @GetMapping("/search")
    public Page<ProductDto> search(
            @RequestBody ProductSearchCondition cond,
            @PageableDefault(size = 2) Pageable pageable) {
        return query.searchProductList(cond, pageable);
    }
}

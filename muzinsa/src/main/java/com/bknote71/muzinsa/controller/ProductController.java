package com.bknote71.muzinsa.controller;

import com.bknote71.muzinsa.domain.account.Account;
import com.bknote71.muzinsa.secuirty.AccountContext;
import com.bknote71.muzinsa.service.ProductCommandService;
import com.bknote71.muzinsa.dto.product.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    // todo: 상품 등록, 수정, 삭제 <<
    private final ProductCommandService productService;

    @PostMapping("/register")
    public ProductDto register(@RequestBody ProductDto dto, @AuthenticationPrincipal AccountContext accountContext) {
        return productService.registerProduct(dto);
    }

    // 수정: 제품 상태 변경, 이름 변경, 가격 변경, 수량 변경, 소개 변경, 옵션 변경
    // --> 일일히 change 메소드 호출 ㄱㄱ
    @PostMapping("/update")
    public ProductDto update(@RequestBody ProductDto dto) {
        // productId 는 필수
        if (dto.getProductId() == null) {
            throw new NullPointerException("id가 null 입니다.");
        }

        return productService.changeProduct(dto);
    }

    @PostMapping("/sub/{productId}")
    public Boolean subscription(Long productId, @AuthenticationPrincipal AccountContext ac) {
        Account account = ac.account();
        return productService.subscribeRestockAlarm(productId, account);
    }


    @DeleteMapping("/delete/{productId}")
    public void delete(@PathVariable Long productId) {

        if (productId == null) {
            throw new NullPointerException("id가 null 입니다.");
        }
        productService.deleteProduct(productId);
    }
}

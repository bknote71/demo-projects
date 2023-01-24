package com.bknote71.muzinsa.controller;

import com.bknote71.muzinsa.dto.BrandDto;
import com.bknote71.muzinsa.service.BrandCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandCommandService brandService;

    @PostMapping("/register")
    public Long register(@RequestBody BrandDto dto) {
        return brandService.register(dto);
    }

}

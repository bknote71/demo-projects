package com.bknote71.muzinsa.service;

import com.bknote71.muzinsa.domain.brand.Brand;
import com.bknote71.muzinsa.domain.repository.BrandRepository;
import com.bknote71.muzinsa.dto.BrandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrandCommandService {

    private final BrandRepository brandRepository;

    public Long register(BrandDto dto) {
        checkIfBrandNameExists(dto.getBrandname());

        Brand brand = Brand.builder()
                .categoryId(dto.getCategoryId())
                .brandname(dto.getBrandname())
                .url(dto.getUrl())
                .companyname(dto.getCompanyname())
                .eid(dto.getEid())
                .address(null)
                .managername(dto.getManagername())
                .phone(dto.getPhone())
                .cellphone(dto.getCellphone())
                .email(dto.getEmail())
                .referenceUrl(null)
                .introduction(null)
                .build();

        Brand savedBrand = brandRepository.save(brand);
        return savedBrand.getId();
    }

    private void checkIfBrandNameExists(String name) {

    }
}

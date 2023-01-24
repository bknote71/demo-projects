package com.bknote71.muzinsa.dto.product;

import com.bknote71.muzinsa.domain.product.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ProductOptionListDto implements Serializable {
    private List<ProductOptionDto> optionDtos;

    public ProductOptionListDto(ProductOptionDto... options) {
        optionDtos = Arrays.asList(options);
    }

    public ProductOptionListDto(List<ProductOption> options) {
        optionDtos = new ArrayList<>();
        for (ProductOption option : options) {
            List<String> values = option.getValues();
            optionDtos.add(new ProductOptionDto(values));
        }
    }

    public List<ProductOption> toProductOptionList() {
        List<ProductOption> productOptions = new ArrayList<>();
        for (ProductOptionDto optionDto : optionDtos) {
            productOptions.add(optionDto.toProductOption());
        }
        return productOptions;
    }
}

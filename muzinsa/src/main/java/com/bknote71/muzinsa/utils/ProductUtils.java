package com.bknote71.muzinsa.utils;

import com.bknote71.muzinsa.dto.product.ProductDto;
import org.springframework.util.StringUtils;

public class ProductUtils {
    public static boolean hasName(ProductDto dto) {
        return StringUtils.hasText(dto.getName());
    }

    public static boolean hasPrice(ProductDto dto) {
        return dto.getPrice() != null;
    }

}

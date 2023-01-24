package com.bknote71.muzinsa.dto.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCondition {
    private String name; // 검색 키워드 --> ( 상품 이름, 태그 ) 를 기준으로 찾는데 태그는 나중에
    private String brand;
    private Long brandId;
    private Integer priceGoe;
    private Integer priceLoe;
}

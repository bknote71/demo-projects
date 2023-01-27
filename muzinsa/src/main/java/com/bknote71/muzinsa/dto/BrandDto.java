package com.bknote71.muzinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {

    // essential
    private Long categoryId;
    private String brandname;
    private String url;
    private String companyname;
    private String eid; // employer id number: 사업자 등록 번호
    private AddressDto address; // 사업장 주소
    private String managername;
    private String phone;
    private String cellphone;
    private String email;

    // not essential
    private String referenceUrl;
    private String introduction;
}

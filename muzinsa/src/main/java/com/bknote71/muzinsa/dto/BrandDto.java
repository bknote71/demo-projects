package com.bknote71.muzinsa.dto;

import com.bknote71.muzinsa.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {

    // essential
    private Long categoryId;
    private String name;
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

package com.bknote71.muzinsa.domain.brand;

import com.bknote71.muzinsa.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BRAND", indexes = {
        @Index(name = "idx__categoryId", columnList = "categoryId"),
        @Index(name = "idx__brandname", columnList = "brandname")
})
@Entity
public class Brand {

    @Id @GeneratedValue
    @Column(name = "BRAND_ID")
    private Long id;
    // essential
    private Long categoryId;
    private String brandname;
    private String url;
    private String companyname;
    private String eid; // employer id number: 사업자 등록 번호
    @Embedded
    private Address address; // 사업장 주소
    private String managername;
    private String phone;
    private String cellphone;
    private String email;

    // not essential
    private String referenceUrl;
    private String introduction;

    // 추가되는 정보
    private Long accountId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "code", column = @Column(name = "CENTER_CODE")),
            @AttributeOverride(name = "juso", column = @Column(name = "CENTER_JUSO")),
            @AttributeOverride(name = "detail", column = @Column(name = "CENTER_DETAIL"))
    })
    private Address distributionCenter;



}

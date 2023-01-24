package com.bknote71.muzinsa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    // 카카오 오픈 api 사용하여 찾을 수 있음
    private String code;
    private String juso;
    private String detail;
}

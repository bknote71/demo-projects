package com.bknote71.muzinsa.dto;

import com.bknote71.muzinsa.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String code;
    private String juso;
    private String detail;

    public Address toAddress() {
        return new Address(code, juso, detail);
    }
}

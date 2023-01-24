package com.bknote71.muzinsa.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto {
    private String username;
    private String password;
    private String name;
    private Long brandId;
}

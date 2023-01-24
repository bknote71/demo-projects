package com.bknote71.muzinsa.dto;

import com.bknote71.muzinsa.domain.Address;
import com.bknote71.muzinsa.domain.order.DeliveryInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Address address;
    private String receiver;
    private String phone;
    private String cellphone;
    private String request;

}

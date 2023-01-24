package com.bknote71.muzinsa.domain.order;

import com.bknote71.muzinsa.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DeliveryInfo {
    @Embedded
    private Address address; // 배송지
    private String receiver;
    private String phone;
    private String cellphone;
    private String request;

}

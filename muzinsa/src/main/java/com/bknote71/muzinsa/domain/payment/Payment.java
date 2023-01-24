package com.bknote71.muzinsa.domain.payment;

import com.bknote71.muzinsa.domain.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class Payment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "PAYMENT_ID")
    private Long id;

    private Long orderId;

    private int price;
    private int finalPrice;

}

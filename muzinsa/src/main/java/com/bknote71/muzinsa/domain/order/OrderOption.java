package com.bknote71.muzinsa.domain.order;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
public class OrderOption {
    @Column(name = "option_value")
    private String value;

    public OrderOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

package com.bknote71.muzinsa.domain.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("PARTNER")
@Entity
public class Partner extends Account implements Serializable {
    private Long brandId;

    @Builder
    public Partner(String username, String password, String name, Long brandId) {
        super(username, password, name, "ROLE_PARTNER");
        this.brandId = brandId;
    }
}

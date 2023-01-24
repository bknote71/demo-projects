package com.bknote71.muzinsa.domain.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("MEMBER")
@Entity
public class Member extends Account implements Serializable {
    private String grade;

    @Builder
    public Member(String username, String password, String name, String grade) {
        super(username, password, name, "ROLE_MEMBER");
        this.grade = grade;
    }
}

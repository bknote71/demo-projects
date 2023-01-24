package com.bknote71.muzinsa.domain.account;

import com.bknote71.muzinsa.domain.Address;
import lombok.*;
import org.springframework.security.access.vote.RoleVoter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
public abstract class Account implements Serializable {

    @Id @GeneratedValue
    @Column(name = "ACCOUNT_ID")
    private Long id;

    private String username;
    private String password;
    private String name;
    // 규칙은 하나씩만: MEMBER, PARTNER, ADMIN
    private String role;

    private String email;
    private String cellphone;

    protected Account(String username, String password, String name, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Account(String username, String password, String name, String role, String email, String cellphone) {
        this(username, password, name, role);
        this.email = email;
        this.cellphone = cellphone;
    }
}

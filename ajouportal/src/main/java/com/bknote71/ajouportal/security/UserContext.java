package com.bknote71.ajouportal.security;

import com.bknote71.ajouportal.domain.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User {

    private UserEntity userEntity;

    public UserContext(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.userEntity = user;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}

package com.bknote71.ajouportal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Resource {
    @Id
    @GeneratedValue
    private Long id;
    private String type; // url, method ?
    private String content;

    // 우선은 string 하나만
    private String role;
}

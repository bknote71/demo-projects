package com.bknote71.ajouportal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private Long professorId;
    private int credit;
    private int years;
    private String semester;
    private String time; // 웖A,화A,수A,목A, << 이런식??
    private String type;
    private String subtype;
    private String lectureRoom;



}

package com.bknote71.ajouportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SugangDto {
    private Long courseId;
    private String name;
    private String code;
    private String professor;
    private int credit;
    private String time;
    private String type;
    private String subtype;
    private String lectureRoom;

}

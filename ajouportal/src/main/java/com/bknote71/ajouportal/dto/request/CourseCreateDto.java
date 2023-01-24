package com.bknote71.ajouportal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseCreateDto {
    private String name;
    private String code;
    private int years;
    private String semester;
    private String type;
    private String subtype;

}

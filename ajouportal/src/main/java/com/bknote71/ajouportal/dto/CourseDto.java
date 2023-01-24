package com.bknote71.ajouportal.dto;

import com.bknote71.ajouportal.domain.Course;
import com.bknote71.ajouportal.utils.ModelMapperUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private Long id;
    private String name;
    private String code;
    private String professor;
    private int credit;
    private int years;
    private String semester;
    private String time;
    private String type;
    private String subtype;
    private String lectureRoom;

    public static CourseDto of(Course course) {
        return ModelMapperUtils.getModelMapper().map(course, CourseDto.class);
    }
}

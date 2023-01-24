package com.bknote71.ajouportal.service;

import com.bknote71.ajouportal.config.CurrentSystem;
import com.bknote71.ajouportal.domain.Course;
import com.bknote71.ajouportal.dto.CourseDto;
import com.bknote71.ajouportal.dto.SugangDto;
import com.bknote71.ajouportal.dto.request.CourseCreateDto;
import com.bknote71.ajouportal.repository.CourseRepository;
import com.bknote71.ajouportal.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CurrentSystem currentSystem;
    private final CourseRepository courseRepository;

    public CourseDto create(CourseCreateDto createDto) {
        Course course = Course.builder()
                .name(createDto.getName())
                .code(createDto.getCode())
                .years(createDto.getYears())
                .semester(createDto.getSemester())
                .type(createDto.getType())
                .subtype(createDto.getSubtype())
                .build();

        Course savedCourse = courseRepository.save(course);

        return CourseDto.of(savedCourse);
    }

    public List<SugangDto> getSugangList(String type, String subtype) {
        List<Course> courses = courseRepository.findByTypeAndSubtypeWithCurrentSystem(
                type, subtype, currentSystem.getYears(), currentSystem.getSemester());

        return ModelMapperUtils.getModelMapper().map(courses, new TypeToken<List<SugangDto>>() {}.getType());
    }
}

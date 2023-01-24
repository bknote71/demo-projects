package com.bknote71.ajouportal.controller;

import com.bknote71.ajouportal.dto.CourseDto;
import com.bknote71.ajouportal.dto.request.CourseCreateDto;
import com.bknote71.ajouportal.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public CourseDto create(@RequestBody CourseCreateDto  createDto) {
        return courseService.create(createDto);
    }


}

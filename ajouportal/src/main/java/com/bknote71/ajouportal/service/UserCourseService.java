package com.bknote71.ajouportal.service;

import com.bknote71.ajouportal.domain.Course;
import com.bknote71.ajouportal.domain.CourseStatus;
import com.bknote71.ajouportal.domain.UserCourse;
import com.bknote71.ajouportal.dto.SugangListDto;
import com.bknote71.ajouportal.repository.CourseRepository;
import com.bknote71.ajouportal.repository.UserCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserCourseService {

    private final UserCourseRepository userCourseRepository;
    private final CourseRepository courseRepository;

    public SugangListDto getListOfCourseByStatus(Long userId, CourseStatus status) {
        List<Course> courses = userCourseRepository.findByUserId(userId).stream()
                .filter(u -> u.getStatus().equals(status))
                .map(u -> courseRepository.findById(u.getCourseId()).orElseThrow())
                .toList();

        return new SugangListDto(courses);
    }

    public Course apply(Long userId, Long courseId) {
        UserCourse applyingCourse = UserCourse.builder()
                .userId(userId)
                .courseId(courseId)
                .status(CourseStatus.APPLY)
                .build();

        userCourseRepository.save(applyingCourse);

        return courseRepository.findById(courseId).get();
    }
}

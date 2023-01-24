package com.bknote71.ajouportal.controller;

import com.bknote71.ajouportal.domain.Course;
import com.bknote71.ajouportal.domain.CourseStatus;
import com.bknote71.ajouportal.domain.UserCourse;
import com.bknote71.ajouportal.domain.UserEntity;
import com.bknote71.ajouportal.dto.SugangListDto;
import com.bknote71.ajouportal.dto.SugangDto;
import com.bknote71.ajouportal.security.UserContext;
import com.bknote71.ajouportal.service.CourseService;
import com.bknote71.ajouportal.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sugang")
public class CourseApplyController {

    private final CourseService courseService;
    private final UserCourseService userCourseService;

    @GetMapping
    public SugangListDto sugangHome(Long userId) {
        return userCourseService.getListOfCourseByStatus(userId, CourseStatus.APPLY);
    }

    @GetMapping("/list")
    public List<SugangDto> sugangList(String type, String subtype) {
        return courseService.getSugangList(type, subtype);
    }

    @PostMapping("/apply")
    public SugangListDto apply(@AuthenticationPrincipal UserContext context, @RequestBody SugangDto sugangDto) { // 수강 dto
        Long courseId = sugangDto.getCourseId();
        UserEntity user = context.getUserEntity();
        // 1. 학점 안넘고, 2. 신청 안한거고 3. 수강했던적 X
        // 1.
        System.out.println(user.getCredit());

        // 2. 신청 안한거? 유저 코스 + apply + not this courseId
        SugangListDto applyingCourse = userCourseService.getListOfCourseByStatus(user.getId(), CourseStatus.APPLY);
        if (applyingCourse.hasCourse(courseId)) {
            System.out.println("이미 신청한 과목임");
        }

        // 3. 수강했던 적이 없음
        SugangListDto completedCourse = userCourseService.getListOfCourseByStatus(user.getId(), CourseStatus.COMPLETE);
        if (completedCourse.hasCourse(courseId)) {
            System.out.println("수강했던 과목임");
        }

        // 수강 신청한 과목(course) 반환
        userCourseService.apply(user.getId(), courseId);
        applyingCourse.addSugang(sugangDto);

        return applyingCourse;
    }
}

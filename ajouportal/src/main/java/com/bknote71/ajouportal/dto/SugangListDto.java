package com.bknote71.ajouportal.dto;

import com.bknote71.ajouportal.domain.Course;
import com.bknote71.ajouportal.domain.UserCourse;
import com.bknote71.ajouportal.utils.ModelMapperUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.TypeToken;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SugangListDto {

    private List<SugangDto> sugangDtoList;

    public SugangListDto(List<Course> courses) {
        this.sugangDtoList = ModelMapperUtils.getModelMapper()
                .map(courses, new TypeToken<List<SugangDto>>() {}.getType());
    }

    public void addSugang(SugangDto sugangDto) {
        sugangDtoList.add(sugangDto);
    }

    public boolean hasCourse(Long courseId) {
        for (SugangDto sugangDto : sugangDtoList) {
            if (courseId.equals(sugangDto.getCourseId()))
                return true;
        }
        return false;
    }
}

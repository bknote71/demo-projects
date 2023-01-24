package com.bknote71.ajouportal.repository;

import com.bknote71.ajouportal.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c " +
            "where c.type = :type and c.subtype = :subtype and c.years = :years and c.semester = :semester")
    List<Course> findByTypeAndSubtypeWithCurrentSystem(
            @Param("type") String type,
            @Param("subtype") String subtype,
            @Param("years") int years,
            @Param("semester") String semester);
}

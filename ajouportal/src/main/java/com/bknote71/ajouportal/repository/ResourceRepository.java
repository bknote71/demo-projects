package com.bknote71.ajouportal.repository;

import com.bknote71.ajouportal.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends  JpaRepository<Resource, Long> {

    @Query("select r from Resource r where r.type = :type")
    List<Resource> findAllByType(@Param("type") String type);
}

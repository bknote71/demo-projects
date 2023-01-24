package com.example.onlineJudge.repository;

import com.example.onlineJudge.domain.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProblemRepository extends MongoRepository<Problem, String> {

    List<Problem> findByNo(Long no);

}

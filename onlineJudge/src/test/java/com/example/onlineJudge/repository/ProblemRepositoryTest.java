package com.example.onlineJudge.repository;

import com.example.onlineJudge.domain.Problem;
import com.example.onlineJudge.domain.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProblemRepositoryTest {

    @Autowired
    ProblemRepository problemRepository;


    @Test
    void init() {
        System.out.println("init");
        final TestCase testcase1 = new TestCase("4 1 2 3 4", "10");
        final TestCase testcase2 = new TestCase("1 1", "1");

        // Arrays.asList, List.of 로 생성한 List 는 추가(add)가 안됨
        List<TestCase> testCases = Arrays.asList(testcase1);
        List<TestCase> testCases2 = List.of(testcase1);

        // 이런 방식으로 생성된 List 에는 추가가 된다.
        final List<TestCase> testCases1 = new ArrayList<>(Arrays.asList(testcase1, testcase2));


        final Problem identity = Problem.builder()
                .title("더하기^^")
                .no(3L)
                .testCases(testCases)
                .build();

        problemRepository.save(identity);

    }


    @Test
    void testcase_조회() {
        System.out.println("조회 시작");

        final List<Problem> byNo = problemRepository.findByNo(2L);
        byNo.stream().forEach(p -> {
            final List<TestCase> testCases = p.getTestCases();
            System.out.println(testCases);
        });

    }
    @Test
    void testcase_활용() {

    }


}
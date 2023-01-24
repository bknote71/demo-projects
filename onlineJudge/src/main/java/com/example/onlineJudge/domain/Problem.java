package com.example.onlineJudge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    private String id;

    private String title;
    private String content;
    private Long no;
    private List<TestCase> testCases = new ArrayList<>();

}

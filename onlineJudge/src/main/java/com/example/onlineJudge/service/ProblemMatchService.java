package com.example.onlineJudge.service;

import com.example.onlineJudge.domain.Submission;

import java.io.IOException;

public interface ProblemMatchService {
    boolean match(Submission submission) throws IOException, InterruptedException;
}

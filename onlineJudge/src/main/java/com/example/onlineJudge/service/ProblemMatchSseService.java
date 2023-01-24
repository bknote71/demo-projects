package com.example.onlineJudge.service;

import com.example.onlineJudge.domain.Submission;
import com.example.onlineJudge.match.ExecuteStrategy;
import com.example.onlineJudge.match.SimpleExecuteStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProblemMatchSseService {

    private final SimpleExecuteStrategyManager matchStrategyFactory;

    @Async
    public void match(Submission submission) throws IOException, InterruptedException {

        System.out.println("match 메서드의 스레드: " + Thread.currentThread());


        final ExecuteStrategy executeStrategy = matchStrategyFactory.getMatchStrategy(submission.getType());

        executeStrategy.createFile(submission);

        executeStrategy.compile(submission.getNo());

        executeStrategy.match(submission.getNo());
    }

}

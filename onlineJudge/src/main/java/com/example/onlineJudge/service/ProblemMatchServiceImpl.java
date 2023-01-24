package com.example.onlineJudge.service;

import com.example.onlineJudge.domain.Submission;
import com.example.onlineJudge.match.ExecuteStrategy;
import com.example.onlineJudge.match.SimpleExecuteStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProblemMatchServiceImpl implements ProblemMatchService {

    private final SimpleExecuteStrategyManager matchStrategyManager;
    
    @Override
    public boolean match(Submission submission) throws IOException, InterruptedException {
        // work flow
        final ExecuteStrategy executeStrategy = matchStrategyManager.getMatchStrategy(submission.getType());

        executeStrategy.createFile(submission);

        executeStrategy.compile(submission.getNo());

        final boolean flag = executeStrategy.match(submission.getNo());
        return flag;
    }
}

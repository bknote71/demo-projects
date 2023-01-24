package com.example.onlineJudge.match;

import com.example.onlineJudge.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JavaExecuteStrategy extends ExecuteStrategy {

    @Autowired
    public JavaExecuteStrategy(ProblemRepository problemRepository, ProcessBuilder processBuilder) {
        super(problemRepository, processBuilder);
    }

    private List<String> compileCommand = new ArrayList<>(Arrays.asList("cmd", "/c", "javac"));
    private List<String> executeCommand = new ArrayList<>(Arrays.asList("cmd", "/c", "java", "-classpath", "C:\\example\\"));

    @Override
    public String getType() {
        return "java";
    }

    @Override
    protected List<String> getCompileCommand(Long no) {
        compileCommand.add("C:\\example\\Hello" + getType() + no.toString() + "." + getType());
        return compileCommand;
    }

    @Override
    protected List<String> getExecuteCommand(Long no) {
        executeCommand.add("Hello" + getType() + no.toString());
        return executeCommand;
    }
}

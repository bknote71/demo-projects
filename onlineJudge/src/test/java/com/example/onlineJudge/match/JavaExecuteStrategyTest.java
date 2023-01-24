package com.example.onlineJudge.match;

import com.example.onlineJudge.domain.Submission;
import com.example.onlineJudge.repository.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class JavaExecuteStrategyTest {
    @Autowired
    private ExecuteStrategy executeStrategy;

    @Autowired
    ProblemRepository problemRepository;

    @Test
    void flow_test() throws IOException, InterruptedException {

        executeStrategy.createFile(new Submission(null, 3L, "import java.util.Scanner;\n" +
                "\n" +
                "public class Hello3 {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        final Scanner in = new Scanner(System.in);\n" +
                "\n" +
                "        final int n = in.nextInt();\n" +
                "        int sum = 0;\n" +
                "\n" +
                "        for (int i = 0; i < n; ++i) {\n" +
                "            final int anInt = in.nextInt();\n" +
                "            sum += anInt;\n" +
                "        }\n" +
                "\n" +
                "        System.out.println(sum);\n" +
                "    }\n" +
                "}"));

        executeStrategy.compile(3L);

        final boolean match = executeStrategy.match(3L);
        System.out.println(match);
    }

}
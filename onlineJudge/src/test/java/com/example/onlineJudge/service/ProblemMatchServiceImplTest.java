package com.example.onlineJudge.service;

import com.example.onlineJudge.domain.Submission;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ProblemMatchServiceImplTest {

    @Autowired
    ProblemMatchServiceImpl problemMatchServiceImpl;

    @Test
    void service_test() throws IOException, InterruptedException {

        final Submission javaCode = Submission.builder()
                .type("java")
                .code("import java.util.Scanner;\n" +
                        "\n" +
                        "public class Hellojava3 {\n" +
                        "\n" +
                        "    public static void main(String[] args) {\n" +
                        "        final Scanner in = new Scanner(System.in);\n" +
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
                        "}")
                .no(3L)
                .build();

        Assertions.assertThat(problemMatchServiceImpl.match(javaCode)).isEqualTo(true);
    }

}
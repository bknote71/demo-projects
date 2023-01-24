package com.example.onlineJudge.match;

import com.example.onlineJudge.domain.Problem;
import com.example.onlineJudge.domain.Submission;
import com.example.onlineJudge.domain.TestCase;
import com.example.onlineJudge.repository.ProblemRepository;
import com.example.onlineJudge.web.ProgressEventEmitterHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Slf4j
public abstract class ExecuteStrategy {

    private final ProblemRepository problemRepository;
    private final ProcessBuilder processBuilder;

    ExecuteStrategy(ProblemRepository problemRepository, ProcessBuilder processBuilder) {
        this.problemRepository = problemRepository;
        this.processBuilder = processBuilder;
    }

    public void createFile(Submission dto) throws IOException {
        final File file = new File("C:\\example\\Hello" + getType() + dto.getNo() + "." + getType());

        try (final BufferedWriter writer
                     = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)))) {

            writer.write(dto.getCode());
            writer.flush();

        }
    }

    protected Process byProcessBuilder(List<String> cmd) throws IOException, InterruptedException {
        return processBuilder.command(cmd).start();
    }

    protected abstract List<String> getCompileCommand(Long no);
    protected abstract List<String> getExecuteCommand(Long no);
    public abstract String getType();

    public void compile(Long no) throws IOException, InterruptedException {
        List<String> compileCommand = getCompileCommand(no);

        final Process process = byProcessBuilder(compileCommand);
        process.waitFor();
    }

    public boolean match(Long no) throws IOException, InterruptedException {

        final List<String> executeCommand = getExecuteCommand(no);
        
        // 프로세스 실행
        final Process process = processBuilder.command(executeCommand).start();

        // -------------------------------------------------
        // 1. db 에서 no에 해당하는 문제에 대한 test case 를 찾은 후 실행할 파일의 input 에 넣어준다.
        // 2. 실행 결과와 test case 에 대한 결과를 비교한다.
        // -------------------------------------------------
        boolean flag = true;

        try (BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
             BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {

            final SseEmitter emitter = ProgressEventEmitterHolder.getEmitter();

            final List<Problem> problems = problemRepository.findByNo(no);
            for (Problem problem : problems) {
                for (TestCase tc : problem.getTestCases()) {
                    final String input = tc.getInput();
                    final String answer = tc.getAnswer();

                    log.info("input: {}, answer: {}", input, answer);

                    stdin.write(input + "\n");
                    stdin.flush();

                    String result = asyncTask(stdout, stderr);

                    log.info("result: {}", result);

                    if (result == null || result.equals("fail") || !answer.equals(result)) {
                        emitter.send(false);
                        emitter.complete();
                        flag = false;
                        break;
                    }

                    emitter.send(true);
                }
                if (flag == false) break;
            }
            emitter.complete();
        }
        process.waitFor();
        return flag;
    }

    private String asyncTask(BufferedReader stdout, BufferedReader stderr) {
        final CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(
                new Supplier<String>() {
                    @Override
                    public String get() {
                        String out = "fail";
                        try {
                            out = stdout.readLine();
                            if(!out.equals("fail"))
                                return out;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return out;
                    }
                });


        final CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(
                new Supplier<String>() {
                    @Override
                    public String get() {
                        String err = "success";
                        try {
                            err = stderr.readLine();
                            if (!err.equals("success"))
                                return "fail";
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return err;
                    }
                });

        String str = null;
        try {
            str = (String) CompletableFuture.anyOf(cf1, cf2)
                    .get(1000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            return "fail";
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str;
    }
}

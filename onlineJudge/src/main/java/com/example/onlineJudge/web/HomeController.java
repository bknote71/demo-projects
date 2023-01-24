package com.example.onlineJudge.web;

import com.example.onlineJudge.domain.Submission;
import com.example.onlineJudge.service.ProblemMatchServiceImpl;
import com.example.onlineJudge.service.ProblemMatchSseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProblemMatchServiceImpl problemMatchServiceImpl;
    private final ProblemMatchSseService problemMatchSseService;

    @GetMapping("/")
    @ResponseBody
    public String home_msg() {
        return "hello";
    }

    @GetMapping("/submit/{no}")
    public String submit_page(@PathVariable Long no, Model model) {
        model.addAttribute("no", no);
        return "submit" + no;
    }

    @PostMapping("/submit/{no}")
    public ResponseEntity<Boolean> submit(@RequestBody Submission submission) throws IOException, InterruptedException {

        return ResponseEntity.ok(problemMatchServiceImpl.match(submission));
    }

    @PostMapping(value = "/submit/{no}/sse", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> submitAndSse(@RequestBody Submission submission) throws IOException, InterruptedException {
        System.out.println("ctl thread: " + Thread.currentThread());
        ProgressEventEmitterHolder.clearHolder();

        problemMatchSseService.match(submission);
        return ResponseEntity.ok(ProgressEventEmitterHolder.getEmitter());
    }

    @GetMapping(value = "/sseTesting", produces = "text/event-stream")
    @ResponseBody
    public SseEmitter sseTestingbybae() {

        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; true; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data("SSE MVC - " + LocalTime.now().toString())
                            .id(String.valueOf(i))
                            .name("sse event - mvc");
                    emitter.send(event);
                    if (i == 5) {
                        // emitter 를 complete 하면 더이상 메시지를 send 하지 못한다.
                        emitter.complete();
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

}

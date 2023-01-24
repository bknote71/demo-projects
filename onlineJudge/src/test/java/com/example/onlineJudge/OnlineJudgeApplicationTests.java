package com.example.onlineJudge;

import com.example.onlineJudge.match.JavaExecuteStrategy;
import com.example.onlineJudge.domain.Message;
import com.example.onlineJudge.domain.Submission;
import com.example.onlineJudge.repository.MessageRepository;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@SpringBootTest
class OnlineJudgeApplicationTests {

	@Autowired
    JavaExecuteStrategy javaExecuteStrategy;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Test
	void mongoTemplate_check() {
		final MongoDatabase db = mongoTemplate.getDb();
		final String name = db.getName();
		System.out.println(name);

	}

	@Test
	void execute_test() throws IOException, InterruptedException {
		javaExecuteStrategy.createFile(new Submission(null, 1L, "public class Hello1 {\n" +
				"    public static void main(String[] args) {\n" +
				"        System.out.println(\"Hello5\");\n" +
				"    }\n" +
				"}"));
		javaExecuteStrategy.compile(1L);
		final boolean result = javaExecuteStrategy.match(1L);
		System.out.println(result);
	}

	@Test
	void contextLoads() {

		final Message message = new Message();
		message.setContent("random data");

		messageRepository.save(message);
	}

}

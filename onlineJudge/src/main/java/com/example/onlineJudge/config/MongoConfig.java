package com.example.onlineJudge.config;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    // MongoClient, MongoDataBaseFactory 는 mongodb 에 대한 설정(uri 커넥션, ..)이다.
    // application.yml 설정 파일에 mongodb 설정값을 기본으로 이용하여 빈으로 등록한다.
    // MongoTemplate 은 mongoClient 나 MongoDataBaseFactory 를 이용하여 생성되고 mongodb 와 통신하는 역할을 한다.
    // ex) 커넥션에 대한 설정: db uri(mongodb://localhost:27017/testDB2)

    // 커스텀하게 빈으로 설정할 수 있다.
    // 커스텀하게 설정하여 MongoTemplate 또한 커스텀하게 설정할 수 있다.
    // 중요: MongoRepository 는 빈으로 등록된 MongoTemplate 을 사용한다.
    // 그렇기 때문에 커스텀한 MongoTemplate 을 빈으로 등록하게 된다면 MongoRepository 도 커스텀한 몽고 템플릿을 사용한다.
//    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "testDB2");
    }



}

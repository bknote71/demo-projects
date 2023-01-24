package com.example.onlineJudge.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.ByteArrayInputStream;


@Document(collection = "msg")
@Data
public class Message {

    @Id
    private String id;
    private String content;

}

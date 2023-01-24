package com.example.onlineJudge.repository;

import com.example.onlineJudge.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, String> {

}

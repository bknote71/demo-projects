package com.bknote71.auction.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ObjectMapperTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void localDateTimeTest() throws IOException {

        objectMapper.registerModule(new JavaTimeModule());

        final LocalDateTime now = LocalDateTime.now();
        final String s = objectMapper.writeValueAsString(now);

        final LocalDateTime localDateTime = objectMapper.readValue(s.getBytes(StandardCharsets.UTF_8), LocalDateTime.class);
        System.out.println(localDateTime);
    }
}

package com.example.common.serializer;

import com.example.common.vo.EmailSendVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

public class CustomSerializer implements Serializer<EmailSendVO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public byte[] serialize(String topic, EmailSendVO data) {
        return objectMapper.writeValueAsBytes(data);
    }
}

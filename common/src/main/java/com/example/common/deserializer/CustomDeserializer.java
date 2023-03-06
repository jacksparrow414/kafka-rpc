package com.example.common.deserializer;

import com.example.common.vo.EmailSendVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Deserializer;

public class CustomDeserializer implements Deserializer<EmailSendVO> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public EmailSendVO deserialize(String topic, byte[] data) {
        return objectMapper.readValue(data, EmailSendVO.class);
    }
}

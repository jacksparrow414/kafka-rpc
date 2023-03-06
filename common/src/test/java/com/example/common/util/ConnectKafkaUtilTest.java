package com.example.common.util;

import com.example.common.service.HelloWorldService;
import com.example.common.vo.EmailSendVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.Future;

class ConnectKafkaUtilTest {

    @SneakyThrows
    @Test
    void obtainProducer() {
        KafkaProducer<String, EmailSendVO> producer = ConnectKafkaUtil.obtainProducer();
        ObjectMapper objectMapper = new ObjectMapper();
        HelloWorldService service = new HelloWorldService("test");
        EmailSendVO emailSendVO = new EmailSendVO();
        emailSendVO.setClassName("com.example.common.service.HelloWorldService");
        emailSendVO.setMethodName("hello");
        emailSendVO.setParamTypes(new Class[]{String.class});
        emailSendVO.setArguments(new Object[]{"hello world first"});
        emailSendVO.setMetadata(objectMapper.writeValueAsString(service));
        ProducerRecord<String, EmailSendVO> record = new ProducerRecord<>("test", "hello-world", emailSendVO);
        Future<RecordMetadata> future = producer.send(record);
        producer.send(record);
        producer.close();
    }

    @Test
    @SneakyThrows
    void obtainConsumer() {
        KafkaConsumer<String, EmailSendVO> consumer = ConnectKafkaUtil.obtainConsumer();
        consumer.subscribe(Collections.singletonList("test"));
        ConsumerRecords<String, EmailSendVO> records = consumer.poll(Duration.ofSeconds(1));
        records.forEach(each -> handler(each.value()));
        consumer.commitSync();
        consumer.close();
    }

    @SneakyThrows
    @Test
    void testJackson() {
        HelloWorldService service = new HelloWorldService("test");
        ObjectMapper objectMapper = new ObjectMapper();
        EmailSendVO emailSendVO = new EmailSendVO();
        emailSendVO.setClassName("com.example.common.service.HelloWorldService");
        emailSendVO.setMethodName("hello");
        emailSendVO.setParamTypes(new Class[]{String.class});
        emailSendVO.setArguments(new Object[]{"hello world"});
        emailSendVO.setMetadata(objectMapper.writeValueAsString(service));
        handler(emailSendVO);
        System.out.println("test");
    }

    @SneakyThrows
    private void handler(EmailSendVO emailSendVO) {
        ObjectMapper objectMapper = new ObjectMapper();
        Class<?> destClass = Class.forName(emailSendVO.getClassName());
        Method method = destClass.getMethod(emailSendVO.getMethodName(), emailSendVO.getParamTypes());
        Object value = objectMapper.readValue(emailSendVO.getMetadata(), destClass);
        method.invoke(value, emailSendVO.getArguments());
    }
}
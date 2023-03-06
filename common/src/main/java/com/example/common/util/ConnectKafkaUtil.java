package com.example.common.util;

import com.example.common.vo.EmailSendVO;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.net.InetAddress;
import java.util.Objects;
import java.util.Properties;

public class ConnectKafkaUtil {

    private static KafkaProducer<String, EmailSendVO> producer;

    private static KafkaConsumer<String, EmailSendVO> consumer;

    private static final String KAFKA_SERVER = "192.168.100.221:9093";

    @SneakyThrows
    public static KafkaProducer<String, EmailSendVO> obtainProducer() {
        if (Objects.isNull(producer)) {
            Properties config = new Properties();
            config.put(ProducerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
            config.put(ProducerConfig.ACKS_CONFIG, "all");
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.example.common.serializer.CustomSerializer");
            producer = new KafkaProducer<>(config);
        }
        return producer;
    }

    @SneakyThrows
    public static KafkaConsumer<String, EmailSendVO> obtainConsumer() {
        if (Objects.isNull(consumer)) {
            Properties config = new Properties();
            config.put(ConsumerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
            config.put(ConsumerConfig.GROUP_ID_CONFIG, "foo");
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.example.common.deserializer.CustomDeserializer");
            consumer = new  KafkaConsumer<>(config);
        }
        return consumer;
    }
}

package com.example.kafkatest

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class JsonSampleConsumer {

    val log = LoggerFactory.getLogger(JsonSampleConsumer::class.java)

    @KafkaListener(topics = ["foobar"], containerFactory = "userKafkaListenerContainerFactory")
    fun consume(user: User) {
        log.info("Message ${user.name} + ${user.age}")
    }
}
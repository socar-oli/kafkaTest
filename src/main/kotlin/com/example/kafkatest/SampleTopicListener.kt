package com.example.kafkatest

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component

@Component
class SampleTopicListener {

    val log = LoggerFactory.getLogger(SampleTopicListener::class.java)

//    @KafkaListener(topics = ["foobar"])
//    fun consume(@Payload data: String) {
//        log.info("Message $data")
//    }

//        메세지 받고 답장 보내기
    @KafkaListener(topics = ["foobar"])
    @SendTo("foobar")
    fun consume2(@Payload data: String): String {
        log.info("Message $data")
        return "Listener got this message ${data}"
    }
}
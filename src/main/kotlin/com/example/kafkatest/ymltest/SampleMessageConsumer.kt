package com.example.kafkatest.ymltest

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SampleMessageConsumer {

    val log = LoggerFactory.getLogger(SampleMessageConsumer::class.java)

    @KafkaListener(topics = ["foo"], groupId = "foo")
    fun listenGroupFoobar(message: String) {
        log.info("Received Message in group foo:  $message")
    }
}
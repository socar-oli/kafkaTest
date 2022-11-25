package com.example.kafkatest.ymltest

import com.example.kafkatest.User
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SampleJsonConsumer {

    val log = LoggerFactory.getLogger(SampleJsonConsumer::class.java)

    @KafkaListener(topics = ["foo"], groupId = "foo")
    fun listenGroupFoobar(user: User) {
        log.info("Received Message in group foo:  $user")
    }
}
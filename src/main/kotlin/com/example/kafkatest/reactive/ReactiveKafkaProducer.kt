package com.example.kafkatest.reactive

import com.example.kafkatest.User
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service

@Service
class ReactiveKafkaProducer(
    val reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, User>
){
    private val log = LoggerFactory.getLogger(ReactiveKafkaProducer::class.java)


    fun send(user: User) {
        log.info("send to topic={}, {}={},", "foo", User::class.java.getSimpleName(), user)
        reactiveKafkaProducerTemplate.send("foo", user)
            .doOnSuccess { senderResult ->
                log.info(
                    "sent {} offset : {}",
                    user,
                    senderResult.recordMetadata().offset()
                )
            }
            .subscribe()
    }
}
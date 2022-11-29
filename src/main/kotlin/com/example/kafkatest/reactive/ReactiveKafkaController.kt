package com.example.kafkatest.reactive

import com.example.kafkatest.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ReactiveKafkaController(
    val producer: ReactiveKafkaProducer
) {

    @GetMapping
    fun produce():String {
        producer.send(User("test", 1234))
        return "hello world"
    }
}
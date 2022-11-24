package com.example.kafkatest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class JsonSampleProducer(
    @Autowired kafkaTemplate: KafkaTemplate<String, User>
) {

    @Bean
    fun sendUserMessage(template: KafkaTemplate<String, User>): ApplicationRunner {
        val user = User("testUser", 30)
        return ApplicationRunner { template.send("foobar", user) }
    }
}

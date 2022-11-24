package com.example.kafkatest

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class SampleTopicProducer {

    @Bean
    fun runner1(template: KafkaTemplate<String?, String?>): ApplicationRunner? {
        return ApplicationRunner { template.send("foobar", "test") }
    }

    @Bean
    fun runner2(template: KafkaTemplate<String?, String?>): ApplicationRunner? {
        return ApplicationRunner { template.send("foobar", "failMessage") }
    }


}
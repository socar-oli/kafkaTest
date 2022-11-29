package com.example.kafkatest.reactive

import com.example.kafkatest.User
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.kafka.receiver.ReceiverOptions


@Configuration
class ReactiveKafkaConsumerConfig {

    @Bean
    fun kafkaReceiverOptions(
        kafkaProperties: KafkaProperties
    ): ReceiverOptions<String, User>? {
        val basicReceiverOptions: ReceiverOptions<String, User> = ReceiverOptions.create<String, User>(kafkaProperties.buildConsumerProperties())
        return basicReceiverOptions.subscription(listOf("foo"))
    }

    @Bean
    fun reactiveKafkaConsumerTemplate(kafkaReceiverOptions: ReceiverOptions<String, User>): ReactiveKafkaConsumerTemplate<String, User> {
        return ReactiveKafkaConsumerTemplate<String, User>(kafkaReceiverOptions)
    }

}
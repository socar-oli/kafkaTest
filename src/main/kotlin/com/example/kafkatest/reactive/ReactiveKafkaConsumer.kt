package com.example.kafkatest.reactive

import com.example.kafkatest.User
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ReactiveKafkaConsumer(
    val reactiveKafkaConsumerTemplate: ReactiveKafkaConsumerTemplate<String, User>
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(ReactiveKafkaConsumer::class.java)

    private fun consumeUser(): Flux<User> {
        return reactiveKafkaConsumerTemplate
            .receiveAutoAck()
            .doOnNext { consumerRecord ->
                log.info(
                    "received key={}, value={} from topic={}, offset={}",
                    consumerRecord.key(),
                    consumerRecord.value(),
                    consumerRecord.topic(),
                    consumerRecord.offset()
                )
            }
            .map { it.value() }
            .doOnNext { user ->
                log.info(
                    "successfully consumed {}={}",
                    User::class.java.getSimpleName(), user
                )
            }
            .doOnError { throwable ->
                log.error(
                    "something bad happened while consuming : {}",
                    throwable.message
                )
            }
    }

    override fun run(vararg args: String) {
        // we have to trigger consumption
        consumeUser().subscribe()
    }
}
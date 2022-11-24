package com.example.kafkatest

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*

@Configuration
class JsonKafkaConsumerConfig {

    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var hosts: String

    @Bean
    fun userKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, User> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, User> =
            ConcurrentKafkaListenerContainerFactory<String, User>()
        factory.consumerFactory = userConsumerFactory()
        return factory
    }


    @Bean
    fun userConsumerFactory(): ConsumerFactory<String, User> {
        return DefaultKafkaConsumerFactory(userConsumerProperties())
    }


    private fun userConsumerProperties(): Map<String, Any> {
        val hostName: String = try {
            InetAddress.getLocalHost().hostName + UUID.randomUUID().toString()
        } catch (e: UnknownHostException) {
            UUID.randomUUID().toString()
        }

        return hashMapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to hosts,
            ConsumerConfig.GROUP_ID_CONFIG to hostName,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "true",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            JsonDeserializer.TRUSTED_PACKAGES to "*"
        )
    }
}
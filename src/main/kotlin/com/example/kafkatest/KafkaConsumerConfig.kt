package com.example.kafkatest

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*


@Configuration
class KafkaConsumerConfig {

    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var hosts: String

//    @Primary
//    @Bean
//    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
//        val containerFactory = ConcurrentKafkaListenerContainerFactory<String, String>()
//        containerFactory.consumerFactory = consumerFactory()
//
//        return containerFactory
//    }
//
    private fun consumerFactory(): ConsumerFactory<in String, in String> {
        return DefaultKafkaConsumerFactory(consumerProperties())
    }

    private fun consumerProperties(): Map<String, Any> {
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
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        )
    }

//    특정 메세지내용을 포함한 메세지만 듣기
//    @Bean
//    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
//        val containerFactory = ConcurrentKafkaListenerContainerFactory<String, String>()
//        containerFactory.consumerFactory = consumerFactory()
//        containerFactory.setRecordFilterStrategy { it.value().contains("fail") }
//        return containerFactory
//    }


    //    메세지 다시 Reply 하는 listener 구현
    @Bean
    fun kafkaListenerContainerFactory(kafkaTemplate: KafkaTemplate<String, Any>): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.consumerFactory = consumerFactory() as ConsumerFactory<in String, in Any>
        factory.setReplyTemplate(kafkaTemplate)
        return factory
    }


}
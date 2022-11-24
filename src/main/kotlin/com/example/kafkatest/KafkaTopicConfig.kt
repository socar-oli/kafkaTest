package com.example.kafkatest

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin


@Configuration
class KafkaTopicConfig {

    //        KafkaAdmin 빈은 브로커에서 새로운 빈을 생성하는 책임을 가지고 있는데 스프링 부트에서는 KafkaAdmin 빈을 자동으로 등록해줌
    @Bean
    fun topic1(): NewTopic? {
        return TopicBuilder.name("topic1").build()
    }

    //    스프링 부트가 아닌 어플리케이션에서는 다음과 같이 KafkaAdmin을 등록해서 사용해줄 수 있음
//    @Bean
//    open fun admin(): KafkaAdmin? {
//        val configs: MutableMap<String, Any> = HashMap()
//        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
//        return KafkaAdmin(configs)
//    }


}
package com.example.kafkatest.ymltest

import com.example.kafkatest.User
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback

@Component
class SampleJsonProducer {

    val log = LoggerFactory.getLogger(SampleJsonProducer::class.java)


    @Bean
    fun sendMessage(template: KafkaTemplate<String, User>): String {
        val user = User("testUser", 30)
        val future: ListenableFuture<SendResult<String, User>> = template.send("foo", user)
        future.addCallback(object : ListenableFutureCallback<SendResult<String, User>> {

            override fun onSuccess(result: SendResult<String, User>?) {
                log.info("Sent message=[$user] with offset=[" + result!!.recordMetadata.offset() + "]")
            }

            override fun onFailure(ex: Throwable) {
                log.info("Unable to send message=[$user] due to : + ${ex.message}")
            }
        })
        return ""
    }
}
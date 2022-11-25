package com.example.kafkatest.ymltest

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback

@Component
class SampleMessageProducer() {

    val log = LoggerFactory.getLogger(SampleMessageProducer::class.java)

    @Bean
    fun sendMessage(template: KafkaTemplate<String, String>): String {
        val message: String = "testMessage"
        val future: ListenableFuture<SendResult<String, String>> = template.send("foo", message)
        future.addCallback(object : ListenableFutureCallback<SendResult<String, String>> {

            override fun onSuccess(result: SendResult<String, String>?) {
                log.info("Sent message=[$message] with offset=[" + result!!.recordMetadata.offset() + "]")
            }

            override fun onFailure(ex: Throwable) {
                log.info("Unable to send message=[$message] due to : + ${ex.message}")
            }
        })
        return ""
    }
}
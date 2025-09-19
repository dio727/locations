package com.api.getcep.integrations.rabbitmq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.cloud.stream.function.StreamBridge
import java.util.function.Consumer


@SpringBootApplication
class LocationConsumer {

    @Bean
    fun saveLocationConsumer(): Consumer<Message<String>> = Consumer { msg ->
        println("SAVE recebido: ${msg.payload}")
    }

    @Bean
    fun updateLocationConsumer(): Consumer<Message<String>> = Consumer { msg ->
        println("UPDATE recebido: ${msg.payload}")
    }

    @Bean
    fun deleteLocationConsumer(): Consumer<Message<String>> = Consumer { msg ->
        println("DELETE recebido: ${msg.payload}")
    }

    @Bean
    fun producer(streamBridge: StreamBridge) = LocationProducer(streamBridge)
}
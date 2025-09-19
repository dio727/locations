package com.api.getcep.integrations.rabbitmq

import org.springframework.stereotype.Service
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.messaging.support.MessageBuilder

@Service
class LocationProducer(private val streamBridge: StreamBridge) {

    enum class Operation(val destination: String) {
        DELETE("delete-location"),
        SAVE("save-location"),
        UPDATE("update-location")
    }

    fun send(message: String, operation: Operation) {
        streamBridge.send("${operation.destination}-out-0", MessageBuilder.withPayload(message).build())
        println("Mensagem enviada para ${operation.name}: $message")
    }
}

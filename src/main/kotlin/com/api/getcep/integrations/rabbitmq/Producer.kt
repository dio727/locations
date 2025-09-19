package com.api.getcep.integrations.rabbitmq

import com.api.getcep.config.RabbitConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class Producer(private val rabbitTemplate: RabbitTemplate) {

    enum class Operation(val exchange: String, val routingKey: String) {
        DELETE(RabbitConfig.DELETE_EXCHANGE, RabbitConfig.DELETE_KEY),
        SAVE(RabbitConfig.SAVE_EXCHANGE, RabbitConfig.SAVE_KEY),
        UPDATE(RabbitConfig.UPDATE_EXCHANGE, RabbitConfig.UPDATE_KEY)
    }

    fun send(message: String, operation: Operation) {
        rabbitTemplate.convertAndSend(operation.exchange, operation.routingKey, message)
        println("Mensagem enviada para ${operation.name}: $message")
    }
}
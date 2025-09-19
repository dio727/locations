package com.api.getcep.integrations.rabbitmq

import com.api.getcep.config.RabbitConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class Producer(private val rabbitTemplate: RabbitTemplate) {
    fun send(message: String) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "test-key", message)
        println("Mensagem enviada: $message")
    }
}
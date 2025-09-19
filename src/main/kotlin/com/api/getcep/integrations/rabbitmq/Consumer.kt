package com.api.getcep.integrations.rabbitmq

import com.api.getcep.config.RabbitConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class Consumer {
    @RabbitListener(queues = [RabbitConfig.QUEUE_NAME])
    fun receive(message: String) {
        println("Mensagem recebida: $message")
    }
}
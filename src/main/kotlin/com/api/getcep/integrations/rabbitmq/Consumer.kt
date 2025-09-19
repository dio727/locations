package com.api.getcep.integrations.rabbitmq

import com.api.getcep.config.RabbitConfig
import com.api.getcep.config.RabbitOperation
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service

@Service
class Consumer {

    @RabbitListener(queues = [
        RabbitConfig.DELETE_QUEUE,
        RabbitConfig.SAVE_QUEUE,
        RabbitConfig.UPDATE_QUEUE
    ])
    fun receive(message: String, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) routingKey: String) {
        when (routingKey) {
            RabbitConfig.DELETE_KEY -> println("DELETE recebido: $message")
            RabbitConfig.SAVE_KEY -> println("SAVE recebido: $message")
            RabbitConfig.UPDATE_KEY -> println("UPDATE recebido: $message")
            else -> println("Mensagem recebida em fila desconhecida: $message")
        }
    }
}
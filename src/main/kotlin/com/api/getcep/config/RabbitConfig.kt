package com.api.getcep.config

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {
    companion object {
        const val QUEUE_NAME = "test-queue"
        const val EXCHANGE_NAME = "test-exchange"
    }

    @Bean
    fun queue() = Queue(QUEUE_NAME, false)

    @Bean
    fun exchange() = DirectExchange(EXCHANGE_NAME)

    @Bean
    fun binding(queue: Queue, exchange: DirectExchange): org.springframework.amqp.core.Binding =
        BindingBuilder.bind(queue).to(exchange).with("test-key")
}
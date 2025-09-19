package com.api.getcep.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    companion object {
        // Queues
        const val DELETE_QUEUE = "delete-location-queue"
        const val SAVE_QUEUE = "save-location-queue"
        const val UPDATE_QUEUE = "update-location-queue"

        // Exchanges
        const val DELETE_EXCHANGE = "delete-location-exchange"
        const val SAVE_EXCHANGE = "save-location-exchange"
        const val UPDATE_EXCHANGE = "update-location-exchange"

        // Routing Keys
        const val DELETE_KEY = "delete-location-key"
        const val SAVE_KEY = "save-location-key"
        const val UPDATE_KEY = "update-location-key"
    }

    // --- Queues ---
    @Bean
    fun deleteQueue() = Queue(DELETE_QUEUE, false)

    @Bean
    fun saveQueue() = Queue(SAVE_QUEUE, false)

    @Bean
    fun updateQueue() = Queue(UPDATE_QUEUE, false)

    // --- Exchanges ---
    @Bean
    fun deleteExchange() = DirectExchange(DELETE_EXCHANGE)

    @Bean
    fun saveExchange() = DirectExchange(SAVE_EXCHANGE)

    @Bean
    fun updateExchange() = DirectExchange(UPDATE_EXCHANGE)

    // --- Bindings ---
    @Bean
    fun deleteBinding(): Binding? = BindingBuilder.bind(deleteQueue()).to(deleteExchange()).with(DELETE_KEY)

    @Bean
    fun saveBinding(): Binding? = BindingBuilder.bind(saveQueue()).to(saveExchange()).with(SAVE_KEY)

    @Bean
    fun updateBinding(): Binding? = BindingBuilder.bind(updateQueue()).to(updateExchange()).with(UPDATE_KEY)
}

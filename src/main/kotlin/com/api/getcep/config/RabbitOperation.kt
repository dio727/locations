package com.api.getcep.config

enum class RabbitOperation(val queue: String, val exchange: String, val routingKey: String) {
    DELETE("delete-location-queue", "delete-location-exchange", "delete-location-key"),
    SAVE("save-location-queue", "save-location-exchange", "save-location-key"),
    UPDATE("update-location-queue", "update-location-exchange", "update-location-key")
}
package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.integrations.rabbitmq.Producer
import com.api.getcep.mappers.toLocationEntity
import org.springframework.stereotype.Service

@Service
class DeleteLocationService(private val locationRepository: LocationRepository, private val getLocationByIdService : GetLocationByIdService, private val producer: Producer) {
    fun deleteLocation(idLocation: Long) {
        val location = getLocationByIdService.getLocationById(idLocation).toLocationEntity()
        locationRepository.delete(location)
        producer.send("Location deletada com ID: ${location.idLocation}", Producer.Operation.DELETE)
    }
}
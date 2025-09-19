package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.dtos.UpdateLocationDTO
import com.api.getcep.integrations.rabbitmq.Producer
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.mappers.toLocationEntity
import com.api.getcep.mappers.toUpdateLocationEntity
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitProducerProperties
import org.springframework.stereotype.Service

@Service
class UpdateLocationService(
    private val locationRepository: LocationRepository,
    private val getLocationByIdService: GetLocationByIdService,
    private val producer: Producer
) {
    fun updateLocation(idLocation: Long, updateDTO: UpdateLocationDTO): LocationDTO {
        val locationDto = getLocationByIdService.getLocationById(idLocation)
        val locationEntity = locationDto.toLocationEntity().toUpdateLocationEntity(updateDTO)
        val updated = locationRepository.save(locationEntity)

        producer.send("Location atualizada com ID: ${updated.idLocation}", Producer.Operation.UPDATE)

        return updated.toLocationDTO()
    }
}
package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.mappers.toLocationEntity
import com.api.getcep.integrations.rabbitmq.Producer
import org.springframework.stereotype.Service

@Service
class SaveLocationByCepService(
    private val locationRepository: LocationRepository,
    private val fetchLocationService: FetchLocationService,
    private val getLocationByCepService: GetLocationByCepService,
    private val producer: Producer
) {
    fun saveLocationByCep(cep: String): LocationDTO {

        getLocationByCepService.checkCepExists(cep)

        val apiCepLocationEntity = fetchLocationService.fetchByCep(cep).toLocationEntity()
        val savedEntity = locationRepository.save(apiCepLocationEntity)

        producer.send("Location salva com ID: ${savedEntity.idLocation}")

        return savedEntity.toLocationDTO()
    }
}
package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.CepAlreadyExistsException
import com.api.getcep.exceptions.CepNotFoundException
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.mappers.toLocationEntity
import org.springframework.stereotype.Service

@Service
class SaveLocationByCepService(
    private val locationRepository: LocationRepository,
    private val fetchLocationService: FetchLocationService,
    private val getLocationByCepService: GetLocationByCepService
) {
    fun saveLocationByCep(cep: String): LocationDTO {

        getLocationByCepService.checkCepExists(cep)

        val apiCepLocationEntity = fetchLocationService.fetchByCep(cep).toLocationEntity()

        val savedEntity = locationRepository.save(apiCepLocationEntity)

        return savedEntity.toLocationDTO()
    }
}
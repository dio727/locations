package com.api.getcep.services

import com.api.getcep.domain.location.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.CepAlreadyExistsException
import com.api.getcep.exceptions.CepNotFoundException
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.mappers.toLocationEntity
import org.springframework.stereotype.Service

@Service
class SaveLocationByCepService(
    private val locationRepository: LocationRepository,
    private val fetchLocationService: FetchLocationService
) {
    fun saveLocationByCep(cep: String): LocationDTO {

        locationRepository.findByCep(cep)?.let {
            throw CepAlreadyExistsException(cep)
        }

        val apiCepLocationDTO = fetchLocationService.fetchByCep(cep)
        val locationEntity = apiCepLocationDTO.toLocationEntity()
        val savedEntity = locationRepository.save(locationEntity)

        return savedEntity.toLocationDTO()
    }
}
package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.mappers.toLocationDTO
import org.springframework.stereotype.Service

@Service
class GetAllLocationsService(private val locationRepository: LocationRepository) {
    fun getAllLocations(): List<LocationDTO> {
        return locationRepository.findAll().map{
            it.toLocationDTO()
        }
    }
}
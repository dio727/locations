package com.api.getcep.services

import com.api.getcep.domain.location.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.mappers.toLocationDTO
import org.springframework.stereotype.Service

@Service
class GetLocationByIdService(private val locationRepository: LocationRepository) {
    fun getLocationById(idLocation: Long): LocationDTO {
        val locationModel = locationRepository.findById(idLocation)
            .orElseThrow { LocationNotFoundException(idLocation) }
        return locationModel.toLocationDTO()
    }
}
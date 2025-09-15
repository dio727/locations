package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.exceptions.LocationNotFoundException
import org.springframework.stereotype.Service

@Service
class DeleteLocationService(private val locationRepository: LocationRepository) {
    fun deleteLocation(idLocation: Long) {
        val location = locationRepository.findById(idLocation)
            .orElseThrow { LocationNotFoundException(idLocation) }
        locationRepository.delete(location)
    }
}
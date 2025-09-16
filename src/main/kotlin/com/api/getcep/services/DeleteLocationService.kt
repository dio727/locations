package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.mappers.toLocationEntity
import org.springframework.stereotype.Service

@Service
class DeleteLocationService(private val locationRepository: LocationRepository, private val getLocationByIdService : GetLocationByIdService) {
    fun deleteLocation(idLocation: Long) {
        val location = getLocationByIdService.getLocationById(idLocation).toLocationEntity()
        locationRepository.delete(location)
    }
}
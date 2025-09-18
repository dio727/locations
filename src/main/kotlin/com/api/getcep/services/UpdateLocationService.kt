package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.dtos.UpdateLocationDTO
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.mappers.toLocationEntity
import com.api.getcep.mappers.toUpdateLocationEntity
import org.springframework.stereotype.Service

@Service
class UpdateLocationService(
    private val locationRepository: LocationRepository,
    private val getLocationByIdService: GetLocationByIdService
) {
    fun updateLocation(idLocation: Long, updateDTO: UpdateLocationDTO): LocationDTO {
        val locationDto = getLocationByIdService.getLocationById(idLocation)
        val locationEntity = locationDto.toLocationEntity().toUpdateLocationEntity(updateDTO)
        val updated = locationRepository.save(locationEntity)
        return updated.toLocationDTO()
    }
}
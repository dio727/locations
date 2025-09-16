package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.dtos.UpdateLocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.mappers.toLocationEntity
import org.springframework.stereotype.Service

@Service
class UpdateLocationService(private val locationRepository: LocationRepository, private val getLocationByIdService: GetLocationByIdService) {
    fun updateLocation(idLocation: Long, updateDTO: UpdateLocationDTO): LocationDTO {

        val location = getLocationByIdService.getLocationById(idLocation).toLocationEntity()

        location.logradouro = updateDTO.logradouro
        location.complemento = updateDTO.complemento
        location.unidade = updateDTO.unidade
        location.bairro = updateDTO.bairro
        location.localidade = updateDTO.localidade
        location.ibge = updateDTO.ibge
        location.gia = updateDTO.gia
        location.siafi = updateDTO.siafi

        val updated = locationRepository.save(location)
        return updated.toLocationDTO()
    }
}
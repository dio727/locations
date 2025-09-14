package com.api.getcep.services

import com.api.getcep.domain.location.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.dtos.UpdateLocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.mappers.toLocationDTO
import org.springframework.stereotype.Service

@Service
class UpdateLocationService(private val locationRepository: LocationRepository) {
    fun updateLocation(idLocation: Long, updateDTO: UpdateLocationDTO): LocationDTO {
        val location = locationRepository.findById(idLocation)
            .orElseThrow { LocationNotFoundException(idLocation) }

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
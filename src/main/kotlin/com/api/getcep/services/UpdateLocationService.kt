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

        val location = getLocationByIdService.getLocationById(idLocation)

        location.apply {
            this.bairro = updateDTO.bairro
            this.logradouro = updateDTO.logradouro
            this.complemento = updateDTO.complemento
            this.unidade = updateDTO.unidade
            this.bairro = updateDTO.bairro
            this.localidade = updateDTO.localidade
            this.ibge = updateDTO.ibge
            this.gia = updateDTO.gia
            this.siafi = updateDTO.siafi
        }

        val updated = locationRepository.save(location.toLocationEntity())
        return updated.toLocationDTO()
    }
}
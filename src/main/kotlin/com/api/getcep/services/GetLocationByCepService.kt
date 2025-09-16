package com.api.getcep.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.exceptions.CepAlreadyExistsException
import org.springframework.stereotype.Service

@Service
class GetLocationByCepService(private val locationRepository: LocationRepository) {

    fun checkCepExists(cep: String) {
        locationRepository.findByCep(cep)?.let {
            throw CepAlreadyExistsException(cep)
        }
    }
}
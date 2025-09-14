package com.api.getcep.services

import com.api.getcep.client.GetLocationClient
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.CepNotFoundException
import com.api.getcep.exceptions.InvalidCepFormatException
import com.api.getcep.mappers.toLocationDTO
import org.springframework.stereotype.Service

@Service
class FetchLocationService(private val getLocationClient: GetLocationClient) {
    fun fetchByCep(cep: String): LocationDTO {
        try {
            val response = getLocationClient.getLocationByCep(cep)
            return response.toLocationDTO()
        } catch (e: feign.FeignException.BadRequest) {
            throw InvalidCepFormatException(cep)
        } catch (e: feign.FeignException.NotFound) {
            throw CepNotFoundException(cep)
        }
    }
}
package com.api.getcep.integrationTest.builder

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.dtos.LocationDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.api.getcep.controllers.response.LocationResponse

class LocationBuilder(private val objectMapper: ObjectMapper) {

    fun create(
        idLocation: Long? = null,
        cep: String = "01001-000",
        logradouro: String = "Praça da Sé",
        complemento: String? = null,
        unidade: String? = null,
        bairro: String = "Sé",
        localidade: String = "São Paulo",
        uf: String = "SP",
        estado: String = "São Paulo",
        regiao: String = "Sudeste",
        ibge: String = "3550308",
        gia: String? = "1004",
        ddd: String = "11",
        siafi: String = "7107"
    ): LocationEntity {
        return LocationEntity(
            idLocation = idLocation,
            cep = cep,
            logradouro = logradouro,
            complemento = complemento,
            unidade = unidade,
            bairro = bairro,
            localidade = localidade,
            uf = uf,
            estado = estado,
            regiao = regiao,
            ibge = ibge,
            gia = gia,
            ddd = ddd,
            siafi = siafi
        )
    }

    fun toDTO(entity: LocationEntity): LocationDTO {
        return LocationDTO(
            idLocation = entity.idLocation,
            cep = entity.cep,
            logradouro = entity.logradouro,
            complemento = entity.complemento,
            unidade = entity.unidade,
            bairro = entity.bairro,
            localidade = entity.localidade,
            uf = entity.uf,
            estado = entity.estado,
            regiao = entity.regiao,
            ibge = entity.ibge,
            gia = entity.gia,
            ddd = entity.ddd,
            siafi = entity.siafi
        )
    }

    fun toResponse(entity: LocationEntity): LocationResponse {
        return LocationResponse(
            idLocation = entity.idLocation ?: 0,
            cep = entity.cep,
            logradouro = entity.logradouro,
            complemento = entity.complemento,
            unidade = entity.unidade,
            bairro = entity.bairro,
            localidade = entity.localidade,
            uf = entity.uf,
            estado = entity.estado,
            regiao = entity.regiao,
            ibge = entity.ibge,
            gia = entity.gia,
            ddd = entity.ddd,
            siafi = entity.siafi
        )
    }

    fun toJson(entity: LocationEntity): String =
        objectMapper.writeValueAsString(toResponse(entity))

    fun toJson(dto: LocationDTO): String =
        objectMapper.writeValueAsString(toResponse(LocationEntity(
            idLocation = dto.idLocation,
            cep = dto.cep,
            logradouro = dto.logradouro,
            complemento = dto.complemento,
            unidade = dto.unidade,
            bairro = dto.bairro,
            localidade = dto.localidade,
            uf = dto.uf,
            estado = dto.estado,
            regiao = dto.regiao,
            ibge = dto.ibge,
            gia = dto.gia,
            ddd = dto.ddd,
            siafi = dto.siafi
        )))

    fun getApiCepJson(
        cep: String = "01001-000",
        logradouro: String = "Praça da Sé",
        complemento: String? = null,
        unidade: String? = null,
        bairro: String = "Sé",
        localidade: String = "São Paulo",
        uf: String = "SP",
        estado: String = "São Paulo",
        regiao: String = "Sudeste",
        ibge: String = "3550308",
        gia: String? = "1004",
        ddd: String = "11",
        siafi: String = "7107"
    ): String {
        val map = mapOf(
            "cep" to cep,
            "logradouro" to logradouro,
            "complemento" to complemento,
            "unidade" to unidade,
            "bairro" to bairro,
            "localidade" to localidade,
            "uf" to uf,
            "estado" to estado,
            "regiao" to regiao,
            "ibge" to ibge,
            "gia" to gia,
            "ddd" to ddd,
            "siafi" to siafi
        )
        return objectMapper.writeValueAsString(map)
    }
}



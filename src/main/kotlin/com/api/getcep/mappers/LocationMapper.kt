package com.api.getcep.mappers

import com.api.getcep.integrations.client.response.ApiCepLocationResponse
import com.api.getcep.controllers.response.LocationResponse
import com.api.getcep.controllers.request.UpdateLocationRequest
import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.dtos.UpdateLocationDTO

fun LocationEntity.toLocationDTO(): LocationDTO =
    LocationDTO(
        idLocation = this.idLocation,
        cep = this.cep,
        logradouro = this.logradouro,
        complemento = this.complemento,
        unidade = this.unidade,
        bairro = this.bairro,
        localidade = this.localidade,
        uf = this.uf,
        estado = this.estado,
        regiao = this.regiao,
        ibge = this.ibge,
        gia = this.gia,
        ddd = this.ddd,
        siafi = this.siafi
    )

fun LocationDTO.toLocationResponse(): LocationResponse =
    LocationResponse(
        idLocation = this.idLocation ?: 0,
        cep = this.cep,
        logradouro = this.logradouro,
        complemento = this.complemento,
        unidade = this.unidade,
        bairro = this.bairro,
        localidade = this.localidade,
        uf = this.uf,
        estado = this.estado,
        regiao = this.regiao,
        ibge = this.ibge,
        gia = this.gia,
        ddd = this.ddd,
        siafi = this.siafi
    )

fun ApiCepLocationResponse.toLocationDTO(): LocationDTO =
    LocationDTO(
        idLocation = null,
        cep = this.cep,
        logradouro = this.logradouro,
        complemento = this.complemento,
        unidade = this.unidade,
        bairro = this.bairro,
        localidade = this.localidade,
        uf = this.uf,
        estado = this.estado,
        regiao = this.regiao,
        ibge = this.ibge,
        gia = this.gia,
        ddd = this.ddd,
        siafi = this.siafi
    )

fun LocationDTO.toLocationEntity(): LocationEntity =
    LocationEntity(
        idLocation = this.idLocation,
        cep = this.cep,
        logradouro = this.logradouro,
        complemento = this.complemento,
        unidade = this.unidade,
        bairro = this.bairro,
        localidade = this.localidade,
        uf = this.uf,
        estado = this.estado,
        regiao = this.regiao,
        ibge = this.ibge,
        gia = this.gia,
        ddd = this.ddd,
        siafi = this.siafi
    )

fun UpdateLocationRequest.toUpdateLocationDTO(): UpdateLocationDTO =
    UpdateLocationDTO(
        logradouro = this.logradouro,
        complemento = this.complemento,
        unidade = this.unidade,
        bairro = this.bairro,
        localidade = this.localidade,
        ibge = this.ibge,
        gia = this.gia,
        siafi = this.siafi,
        regiao = this.regiao
    )

fun LocationEntity.toUpdateLocationEntity(updateDTO: UpdateLocationDTO): LocationEntity = this.apply {
    logradouro = updateDTO.logradouro
    complemento = updateDTO.complemento
    unidade = updateDTO.unidade
    bairro = updateDTO.bairro
    localidade = updateDTO.localidade
    ibge = updateDTO.ibge
    gia = updateDTO.gia
    siafi = updateDTO.siafi
    regiao = updateDTO.regiao
}





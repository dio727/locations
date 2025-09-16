package com.api.getcep.dtos

data class LocationDTO(
    val idLocation: Long?,
    val cep: String,
    var logradouro: String?,
    var complemento: String?,
    var unidade: String?,
    var bairro: String?,
    var localidade: String,
    val uf: String,
    val estado: String?,
    val regiao: String?,
    var ibge: String?,
    var gia: String?,
    val ddd: String?,
    var siafi: String?
)

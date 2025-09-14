package com.api.getcep.dtos

data class UpdateLocationDTO(
    val logradouro: String?,
    val complemento: String?,
    val unidade: String?,
    val bairro: String?,
    val localidade: String,
    val regiao: String?,
    val ibge: String?,
    val gia: String?,
    val siafi: String?
)

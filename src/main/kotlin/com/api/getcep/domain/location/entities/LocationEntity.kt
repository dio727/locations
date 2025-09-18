package com.api.getcep.domain.location.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "locations")
data class LocationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idLocation: Long? = null,

    val cep: String?,
    var logradouro: String?,
    var complemento: String?,
    var unidade: String?,
    var bairro: String?,
    var localidade: String?,
    val uf: String?,
    val estado: String?,
    var regiao: String?,
    var ibge: String?,
    var gia: String?,
    val ddd: String?,
    var siafi: String?
)
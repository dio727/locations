package com.api.getcep.services

import com.api.getcep.domain.location.LocationEntity
import com.api.getcep.domain.location.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.mappers.toLocationDTO
import kotlin.test.Test
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals

class GetAllLocationsServiceTest {
    private val locationRepository: LocationRepository = mockk()
    private val getAllLocationsService = GetAllLocationsService(locationRepository)

    @Test
    fun shouldReturnAListOfLocationDTOWhenLocationsExist() {
        val locations = listOf(
            LocationEntity(
                idLocation = 1L,
                cep = "01001-000",
                logradouro = "Praça da Sé",
                complemento = "lado ímpar",
                unidade = null,
                bairro = "Sé",
                localidade = "São Paulo",
                uf = "SP",
                estado = "São Paulo",
                regiao = "Sudeste",
                ibge = "3550308",
                gia = "1004",
                ddd = "11",
                siafi = "7107"
            ),
            LocationEntity(
                idLocation = 2L,
                cep = "22020-001",
                logradouro = "Avenida Atlântica",
                complemento = null,
                unidade = null,
                bairro = "Copacabana",
                localidade = "Rio de Janeiro",
                uf = "RJ",
                estado = "Rio de Janeiro",
                regiao = "Sudeste",
                ibge = "3304557",
                gia = null,
                ddd = "21",
                siafi = "6001"
            )
        )

        every { locationRepository.findAll() } returns locations

        val result = getAllLocationsService.getAllLocations()
        val expectedDTOs = locations.map { it.toLocationDTO() }

        assertEquals(expectedDTOs, result)
    }

    @Test
    fun shouldReturnAnEmptyListWhenNoLocationsExist() {
        every { locationRepository.findAll() } returns emptyList()
        val result = getAllLocationsService.getAllLocations()

        assertEquals(emptyList<LocationDTO>(), result)
    }
}
package com.api.getcep.services

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.mappers.toLocationDTO
import kotlin.test.Test
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertThrows
import java.util.Optional
import kotlin.test.assertEquals

class GetLocationByIdServiceTest {
    private val locationRepository: LocationRepository = mockk()
    private val getLocationByIdService = GetLocationByIdService(locationRepository)

    @Test
    fun shouldReturnALocationDTOWhenLocationIsFound() {

        val idLocation = 1L
        val locationEntity = LocationEntity(
            idLocation = idLocation,
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
        )

        every { locationRepository.findById(idLocation) } returns Optional.of(locationEntity)

        val result = getLocationByIdService.getLocationById(idLocation)

        val expectedDTO = locationEntity.toLocationDTO()
        assertEquals(expectedDTO, result)
    }

    @Test
    fun shouldThrowLocationNotFoundExceptionWhenLocationIsNotFound() {

        val idLocation = 99L

        every { locationRepository.findById(idLocation) } returns Optional.empty()

        assertThrows(LocationNotFoundException::class.java) {
            getLocationByIdService.getLocationById(idLocation)
        }
    }
}
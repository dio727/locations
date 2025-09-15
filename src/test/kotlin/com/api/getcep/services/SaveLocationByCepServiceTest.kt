package com.api.getcep.services

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.CepAlreadyExistsException
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.mappers.toLocationEntity
import kotlin.test.Test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertThrows
import kotlin.test.assertEquals

class SaveLocationByCepServiceTest {
    private val locationRepository: LocationRepository = mockk()
    private val fetchLocationService: FetchLocationService = mockk()
    private val saveLocationByCepService = SaveLocationByCepService(locationRepository, fetchLocationService)

    @Test
    fun shouldSaveLocationWhenCepDoesNotExist() {
        val cep = "01001-000"

        val fetchedLocationDTO = LocationDTO(
            idLocation = null,
            cep = cep,
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

        val locationEntityToSave = fetchedLocationDTO.toLocationEntity().copy(idLocation = null)
        val savedLocationEntity = locationEntityToSave.copy(idLocation = 1L)

        every { locationRepository.findByCep(cep) } returns null
        every { fetchLocationService.fetchByCep(cep) } returns fetchedLocationDTO

        every { locationRepository.save(any()) } returns savedLocationEntity

        val result = saveLocationByCepService.saveLocationByCep(cep)

        val expectedDTO = savedLocationEntity.toLocationDTO()

        verify(exactly = 1) { locationRepository.save(any()) }
        assertEquals(expectedDTO, result)
    }

    @Test
    fun shouldThrowCepAlreadyExistsExceptionWhenCepExists() {
        val cep = "01001-000"
        val existingLocation = LocationEntity(
            idLocation = 1L,
            cep = cep,
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

        every { locationRepository.findByCep(cep) } returns existingLocation

        assertThrows(CepAlreadyExistsException::class.java) {
            saveLocationByCepService.saveLocationByCep(cep)
        }

        verify(exactly = 0) { fetchLocationService.fetchByCep(any()) }
        verify(exactly = 0) { locationRepository.save(any()) }
    }
}
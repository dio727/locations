package com.api.getcep.unitTests.services

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.CepAlreadyExistsException
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.mappers.toLocationEntity
import com.api.getcep.services.FetchLocationService
import com.api.getcep.services.GetLocationByCepService
import com.api.getcep.services.SaveLocationByCepService
import io.mockk.Runs
import kotlin.test.Test
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class SaveLocationByCepServiceTest(
    @param:MockK
    private val locationRepository: LocationRepository,

    @param:MockK
    private val fetchLocationService: FetchLocationService,

    @param:MockK
    private val getLocationByCepService: GetLocationByCepService,

    @param:InjectMockKs
    private val saveLocationByCepService: SaveLocationByCepService

)// extends
{
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

        every { getLocationByCepService.checkCepExists(cep) } just Runs
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

        every { getLocationByCepService.checkCepExists(cep) } throws CepAlreadyExistsException(cep)

        assertThrows(CepAlreadyExistsException::class.java) {
            saveLocationByCepService.saveLocationByCep(cep)
        }

        verify(exactly = 0) { fetchLocationService.fetchByCep(any()) }
        verify(exactly = 0) { locationRepository.save(any()) }
    }
}

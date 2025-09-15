package com.api.getcep.services

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.UpdateLocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.mappers.toLocationDTO
import kotlin.test.Test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertThrows
import java.util.Optional
import kotlin.test.assertEquals

class UpdateLocationServiceTest {

    private val locationRepository: LocationRepository = mockk()
    private val updateLocationService = UpdateLocationService(locationRepository)

    @Test
    fun shouldUpdateLocationSuccessfullyWhenFound() {
        val idLocation = 1L
        val existingLocation = LocationEntity(
            idLocation = idLocation,
            cep = "01001-000",
            logradouro = "Rua Antiga",
            complemento = "Comp. Antigo",
            unidade = null,
            bairro = "Bairro Velho",
            localidade = "Cidade Velha",
            uf = "SP",
            estado = "São Paulo",
            regiao = "Sudeste",
            ibge = "12345",
            gia = null,
            ddd = "11",
            siafi = "67890"
        )
        val updateDTO = UpdateLocationDTO(
            logradouro = "Rua Nova",
            complemento = "Comp. Novo",
            unidade = "Unidade Nova",
            bairro = "Bairro Novo",
            localidade = "Cidade Nova",
            regiao = "Centro-Oeste",
            ibge = "54321",
            gia = "98765",
            siafi = "09876"
        )
        val updatedLocation = existingLocation.copy(
            logradouro = "Rua Nova",
            complemento = "Comp. Novo",
            unidade = "Unidade Nova",
            bairro = "Bairro Novo",
            localidade = "Cidade Nova",
            ibge = "54321",
            gia = "98765",
            siafi = "09876"
        )

        every { locationRepository.findById(idLocation) } returns Optional.of(existingLocation)
        every { locationRepository.save(updatedLocation) } returns updatedLocation

        val result = updateLocationService.updateLocation(idLocation, updateDTO)

        val expectedDTO = updatedLocation.toLocationDTO()

        verify(exactly = 1) { locationRepository.save(updatedLocation) }
        assertEquals(expectedDTO, result)
    }


    @Test
    fun shouldThrowLocationNotFoundExceptionWhenLocationNotFound() {
        val idLocation = 99L
        val updateDTO = UpdateLocationDTO(
            logradouro = "Rua Nova",
            complemento = "Comp. Novo",
            unidade = "Unidade Nova",
            bairro = "Bairro Novo",
            localidade = "Cidade Nova",
            regiao = "Centro-Oeste",
            ibge = "54321",
            gia = "98765",
            siafi = "09876"
        )

        every { locationRepository.findById(idLocation) } returns Optional.empty()

        assertThrows(LocationNotFoundException::class.java) {
            updateLocationService.updateLocation(idLocation, updateDTO)
        }

        verify(exactly = 0) { locationRepository.save(any()) }
    }
}
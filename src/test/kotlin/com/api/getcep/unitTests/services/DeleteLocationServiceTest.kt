package com.api.getcep.unitTests.services

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.services.DeleteLocationService
import java.util.Optional
import kotlin.test.Test
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows

class DeleteLocationServiceTest {
    private val locationRepository: LocationRepository = mockk()
    private val deleteLocationService = DeleteLocationService(locationRepository)

    @Test
    fun shouldDeleteLocationWhenFound() {
        val location = LocationEntity(
            idLocation = 1L,
            cep = "01001-000",
            logradouro = "Rua Teste",
            complemento = "",
            unidade = null,
            bairro = "Bairro Teste",
            localidade = "São Paulo",
            uf = "SP",
            estado = "São Paulo",
            regiao = "Sudeste",
            ibge = "3550308",
            gia = "1004",
            ddd = "11",
            siafi = "7107"
        )

        every { locationRepository.findById(1L) } returns Optional.of(location)
        justRun { locationRepository.delete(location) }

        deleteLocationService.deleteLocation(1L)

        verify(exactly = 1) { locationRepository.delete(location) }
    }

    @Test
    fun shouldThrowLocationNotFoundExceptionWhenLocationNotFound() {
        every { locationRepository.findById(99L) } returns Optional.empty()

        assertThrows(LocationNotFoundException::class.java) {
            deleteLocationService.deleteLocation(99L)
        }

        verify(exactly = 0) { locationRepository.delete(any()) }
    }
}
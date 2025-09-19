package com.api.getcep.unitTests.services

import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.integrations.rabbitmq.LocationProducer
import com.api.getcep.mappers.toLocationEntity
import com.api.getcep.services.DeleteLocationService
import com.api.getcep.unitTests.services.mock.BaseServiceTest
import io.mockk.MockKAnnotations
import io.mockk.Runs
import kotlin.test.Test
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach

class DeleteLocationServiceTest : BaseServiceTest() {

    private lateinit var deleteLocationService: DeleteLocationService

    @MockK
    private lateinit var producer: LocationProducer


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        deleteLocationService = DeleteLocationService(
            locationRepository,
            getLocationByIdService,
            producer
        )
    }

    @Test
    fun shouldDeleteLocationWhenFound() {
        val locationDTO = LocationDTO(
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

        every { getLocationByIdService.getLocationById(1L) } returns locationDTO
        justRun { locationRepository.delete(any()) }
        every { producer.send(any(), any<LocationProducer.Operation>()) } just Runs

        deleteLocationService.deleteLocation(1L)

        verify(exactly = 1) { locationRepository.delete(locationDTO.toLocationEntity()) }
    }

    @Test
    fun shouldThrowLocationNotFoundExceptionWhenLocationNotFound() {
        every { getLocationByIdService.getLocationById(99L) } throws LocationNotFoundException(99L)

        assertThrows(LocationNotFoundException::class.java) {
            deleteLocationService.deleteLocation(99L)
        }

        verify(exactly = 0) { locationRepository.delete(any()) }
    }
}

package com.api.getcep.unitTests.services

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.dtos.UpdateLocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.integrations.rabbitmq.Producer
import com.api.getcep.services.GetLocationByIdService
import com.api.getcep.services.UpdateLocationService
import io.mockk.Runs
import kotlin.test.Test
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertThrows
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitProducerProperties
import kotlin.test.assertEquals

class UpdateLocationServiceTest {

    private val locationRepository: LocationRepository = mockk()
    private val getLocationByIdService: GetLocationByIdService = mockk()
    private val producer: Producer = mockk()

    private val updateLocationService = UpdateLocationService(
        locationRepository,
        getLocationByIdService,
        producer
    )

    @Test
    fun shouldUpdateLocationSuccessfullyWhenFound() {
        val idLocation = 1L
        val locationDTO = LocationDTO(
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

        every { getLocationByIdService.getLocationById(idLocation) } returns locationDTO
        every { locationRepository.save(any()) } answers { firstArg<LocationEntity>() }
        every { producer.send(any()) } just Runs

        val result = updateLocationService.updateLocation(idLocation, updateDTO)

        val expectedDTO = locationDTO.copy(
            logradouro = updateDTO.logradouro,
            complemento = updateDTO.complemento,
            unidade = updateDTO.unidade,
            bairro = updateDTO.bairro,
            localidade = updateDTO.localidade,
            ibge = updateDTO.ibge,
            gia = updateDTO.gia,
            siafi = updateDTO.siafi,
            regiao = updateDTO.regiao
        )

        verify(exactly = 1) { locationRepository.save(any()) }
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

        every { getLocationByIdService.getLocationById(idLocation) } throws LocationNotFoundException(idLocation)

        assertThrows(LocationNotFoundException::class.java) {
            updateLocationService.updateLocation(idLocation, updateDTO)
        }

        verify(exactly = 0) { locationRepository.save(any()) }
    }
}
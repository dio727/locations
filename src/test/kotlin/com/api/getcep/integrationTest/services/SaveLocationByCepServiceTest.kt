package com.api.getcep.integrationTest.services

import com.api.getcep.domain.location.LocationEntity
import com.api.getcep.domain.location.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.CepAlreadyExistsException
import com.api.getcep.exceptions.CepNotFoundException
import com.api.getcep.exceptions.InvalidCepFormatException
import com.api.getcep.services.FetchLocationService
import com.api.getcep.services.SaveLocationByCepService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class SaveLocationByCepServiceTest @Autowired constructor(
    private val saveLocationByCepService: SaveLocationByCepService,
    private val locationRepository: LocationRepository,
    private val fetchLocationService: FetchLocationService
) {

    @TestConfiguration
    class ServiceTestConfig {
        @Bean
        fun fetchLocationService() = mockk<FetchLocationService>()
    }

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
    }

    @Test
    fun shouldSaveLocationAndReturnLocationDTOWhenCepIsNew() {
        val newCep = "01001-000"
        val locationDTOFromApi = LocationDTO(
            idLocation = null,
            cep = newCep,
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

        every { fetchLocationService.fetchByCep(newCep) } returns locationDTOFromApi

        val savedLocationDTO = saveLocationByCepService.saveLocationByCep(newCep)

        assertNotNull(savedLocationDTO.idLocation)
        assertEquals(newCep, savedLocationDTO.cep)
        assertEquals(1, locationRepository.count())
    }

    @Test
    fun shouldThrowCepAlreadyExistsExceptionWhenCepExists() {
        val existingCep = "01001-000"
        val existingLocation = LocationEntity(
            idLocation = null,
            cep = existingCep,
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
        locationRepository.save(existingLocation)

        assertThrows<CepAlreadyExistsException> {
            saveLocationByCepService.saveLocationByCep(existingCep)
        }
    }

    @Test
    fun shouldThrowInvalidCepFormatExceptionWhenCepFromApiIsInvalid() {
        val invalidCep = "123"

        every { fetchLocationService.fetchByCep(invalidCep) } throws InvalidCepFormatException(invalidCep)

        assertThrows<InvalidCepFormatException> {
            saveLocationByCepService.saveLocationByCep(invalidCep)
        }
    }

    @Test
    fun shouldThrowCepNotFoundExceptionWhenCepFromApiIsNotFound() {
        val notFoundCep = "99999-999"

        every { fetchLocationService.fetchByCep(notFoundCep) } throws CepNotFoundException(notFoundCep)

        assertThrows<CepNotFoundException> {
            saveLocationByCepService.saveLocationByCep(notFoundCep)
        }
    }
}
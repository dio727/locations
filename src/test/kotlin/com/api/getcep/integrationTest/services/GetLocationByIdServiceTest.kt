package com.api.getcep.integrationTest.services

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.services.GetLocationByIdService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class GetLocationByIdServiceTest @Autowired constructor(
    private val getLocationByIdService: GetLocationByIdService,
    private val locationRepository: LocationRepository
) {

    @Test
    fun shouldReturnLocationDTOWhenIdExists() {
        val location = LocationEntity(
            idLocation = null,
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
        val savedLocation = locationRepository.save(location)

        val result = getLocationByIdService.getLocationById(savedLocation.idLocation!!)

        assertEquals(savedLocation.idLocation, result.idLocation)
        assertEquals(savedLocation.cep, result.cep)
        assertEquals(savedLocation.localidade, result.localidade)
    }

    @Test
    fun shouldThrowLocationNotFoundExceptionWhenIdDoesNotExist() {
        val nonExistentId = 999L
        assertThrows<LocationNotFoundException> {
            getLocationByIdService.getLocationById(nonExistentId)
        }
    }
}
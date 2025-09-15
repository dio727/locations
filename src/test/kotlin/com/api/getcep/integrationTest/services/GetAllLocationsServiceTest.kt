package com.api.getcep.integrationTest.services

import com.api.getcep.domain.location.LocationEntity
import com.api.getcep.domain.location.LocationRepository
import com.api.getcep.services.GetAllLocationsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class GetAllLocationsServiceTest @Autowired constructor(
    private val getAllLocationsService: GetAllLocationsService,
    private val locationRepository: LocationRepository
) {

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
    }

    @Test
    fun shouldReturnAListOfLocationDTOWhenLocationsExist() {
        val location1 = LocationEntity(
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
        val location2 = LocationEntity(
            idLocation = null,
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
        locationRepository.saveAll(listOf(location1, location2))

        val result = getAllLocationsService.getAllLocations()

        assertFalse(result.isEmpty())
        assertEquals(2, result.size)
        assertEquals(location1.cep, result[0].cep)
        assertEquals(location2.cep, result[1].cep)
    }

    @Test
    fun shouldReturnAnEmptyListWhenNoLocationsExist() {
        val result = getAllLocationsService.getAllLocations()
        assertTrue(result.isEmpty())
    }
}
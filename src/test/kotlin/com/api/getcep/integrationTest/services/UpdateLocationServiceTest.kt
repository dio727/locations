package com.api.getcep.integrationTest.services

import com.api.getcep.domain.location.LocationEntity
import com.api.getcep.domain.location.LocationRepository
import com.api.getcep.dtos.UpdateLocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.services.UpdateLocationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UpdateLocationServiceTest @Autowired constructor(
    private val updateLocationService: UpdateLocationService,
    private val locationRepository: LocationRepository
) {

    @Test
    fun shouldUpdateLocationAndReturnUpdatedDTOWhenLocationExists() {
        val initialLocation = LocationEntity(
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
        val savedLocation = locationRepository.save(initialLocation)
        val savedId = savedLocation.idLocation!!

        val updateDTO = UpdateLocationDTO(
            logradouro = "Rua Teste",
            complemento = "Apt 101",
            unidade = null,
            bairro = "Centro",
            localidade = "São Paulo",
            regiao = "Sudeste",
            ibge = "3550308",
            gia = "1004",
            siafi = "7107"
        )

        val updatedLocationDTO = updateLocationService.updateLocation(savedId, updateDTO)

        assertNotNull(updatedLocationDTO)
        assertEquals(savedId, updatedLocationDTO.idLocation)
        assertEquals("Rua Teste", updatedLocationDTO.logradouro)
        assertEquals("Apt 101", updatedLocationDTO.complemento)
        assertEquals("Centro", updatedLocationDTO.bairro)

        val persistedLocation = locationRepository.findById(savedId).get()
        assertEquals("Rua Teste", persistedLocation.logradouro)
        assertEquals("Apt 101", persistedLocation.complemento)
        assertEquals("Centro", persistedLocation.bairro)
    }

    @Test
    fun shouldThrowLocationNotFoundExceptionWhenLocationToUpdateDoesNotExist() {
        val nonExistentId = 999L
        val updateDTO = UpdateLocationDTO(
            logradouro = "Rua Teste",
            complemento = "Apt 101",
            unidade = null,
            bairro = "Centro",
            localidade = "São Paulo",
            regiao = "Sudeste",
            ibge = "3550308",
            gia = "1004",
            siafi = "7107"
        )

        assertThrows<LocationNotFoundException> {
            updateLocationService.updateLocation(nonExistentId, updateDTO)
        }
    }
}
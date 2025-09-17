package com.api.getcep.integrationTest.services

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.services.DeleteLocationService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class DeleteLocationServiceTest @Autowired constructor(
    private val deleteLocationService: DeleteLocationService,
    private val locationRepository: LocationRepository
) {

    @Test
    fun shouldDeleteLocationWhenLocationExists() {
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

        deleteLocationService.deleteLocation(savedLocation.idLocation!!)

        val locationExists = locationRepository.findById(savedLocation.idLocation!!).isPresent
        assertFalse(locationExists)
    }

//    @Test
//    fun shouldThrowLocationNotFoundExceptionWhenLocationDoesNotExist() {
//        val nonExistentId = 999L
//
//        assertThrows<LocationNotFoundException> {
//            deleteLocationService.deleteLocation(nonExistentId)
//        }
//        assertTrue(locationRepository.count() == 0L)
//    }
}
package com.api.getcep.integrationTest.controllers

import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.Application
import com.api.getcep.domain.location.entities.LocationEntity
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@AutoConfigureMockMvc
@SpringBootTest(classes = [Application::class])
@ActiveProfiles("test")
class DeleteLocationControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val locationRepository: LocationRepository
) {

    @Test
    fun shouldReturn204NoContentWhenLocationIsSuccessfullyDeleted() {
        val savedLocation: LocationEntity = locationRepository.save(createSampleLocation())

        mockMvc.perform(
            delete("/locations/{idLocation}", savedLocation.idLocation!!)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)

        val locationExists = locationRepository.findById(savedLocation.idLocation!!).isPresent
        Assertions.assertFalse(locationExists)
    }

    @Test
    fun shouldReturn404NotFoundWhenLocationDoesNotExist() {
        val nonExistentId = 999L

        mockMvc.perform(
            delete("/locations/{idLocation}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }

    private fun createSampleLocation(): LocationEntity {
        return LocationEntity(
            idLocation = null,
            cep = "01001-000",
            logradouro = "Praça da Sé",
            complemento = "lado ímpar",
            unidade = "",
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
    }
}
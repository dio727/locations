package com.api.getcep.integrationTest.controllers

import com.api.getcep.integrationTest.builder.LocationBuilder
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import com.api.getcep.integrationTest.mock.BaseIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetLocationByIdControllerTest : BaseIntegrationTest() {

    private lateinit var builder: LocationBuilder

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
        builder = LocationBuilder(objectMapper)
    }

    @Test
    fun shouldReturn200AndLocationWhenIdExists() {
        val savedLocation = locationRepository.save(
            builder.create(cep = "01001-000", logradouro = "Praça da Sé", bairro = "Sé", localidade = "São Paulo", uf = "SP")
        )

        val expectedJson = builder.toJson(savedLocation)

        mockMvc.perform(
            get("/locations/{idLocation}", savedLocation.idLocation!!)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson))
    }

    @Test
    fun shouldReturn404WhenIdDoesNotExist() {
        val nonExistentId = 999L

        mockMvc.perform(
            get("/locations/{idLocation}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }
}

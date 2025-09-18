package com.api.getcep.integrationTest.controllers

import com.api.getcep.integrationTest.builder.LocationBuilder
import com.api.getcep.integrationTest.mock.BaseIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class GetAllLocationsControllerTest: BaseIntegrationTest() {

    private lateinit var builder: LocationBuilder

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
        builder = LocationBuilder(objectMapper)
    }

    @Test
    fun shouldReturn200AndListOfLocationsWhenLocationsExist() {
        val loc1 = locationRepository.save(builder.create(cep = "01001-000", logradouro = "Praça da Sé", bairro = "Sé", localidade = "São Paulo", uf = "SP"))
        val loc2 = locationRepository.save(builder.create(cep = "22020-001", logradouro = "Avenida Atlântica", bairro = "Copacabana", localidade = "Rio de Janeiro", uf = "RJ"))

        val expectedJson = objectMapper.writeValueAsString(listOf(builder.toResponse(loc1), builder.toResponse(loc2)))

        mockMvc.perform(
            get("/locations")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson))
    }

    @Test
    fun shouldReturn200AndEmptyListWhenNoLocationsExist() {
        mockMvc.perform(
            get("/locations")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"))
    }
}


package com.api.getcep.integrationTest.controllers

import com.api.getcep.controllers.GetAllLocationsController
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.services.GetAllLocationsService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(GetAllLocationsController::class)
class GetAllLocationsControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val getAllLocationsService: GetAllLocationsService,
    private val objectMapper: ObjectMapper
) {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun getAllLocationsService() = mockk<GetAllLocationsService>()
    }

    @Test
    fun shouldReturn200AndListOfLocationsWhenLocationsExist() {
        val locationDTOs = listOf(
            LocationDTO(
                idLocation = 1L,
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
            ),
            LocationDTO(
                idLocation = 2L,
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
        )

        every { getAllLocationsService.getAllLocations() } returns locationDTOs

        val expectedJson = objectMapper.writeValueAsString(locationDTOs)

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

        every { getAllLocationsService.getAllLocations() } returns emptyList()

        mockMvc.perform(
            get("/locations")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"))
    }
}
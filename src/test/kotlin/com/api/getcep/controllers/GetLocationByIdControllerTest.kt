package com.api.getcep.controllers

import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.mappers.toLocationResponse
import com.api.getcep.services.GetLocationByIdService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(GetLocationByIdController::class)
class GetLocationByIdControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val getLocationByIdService: GetLocationByIdService,
    private val objectMapper: ObjectMapper
) {
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun getLocationByIdService() = mockk<GetLocationByIdService>()
    }

    @Test
    fun shouldReturn200AndLocationWhenIdExists() {
        val idLocation = 1L
        val locationDTO = LocationDTO(
            idLocation = idLocation,
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
        val locationResponse = locationDTO.toLocationResponse()
        val expectedJson = objectMapper.writeValueAsString(locationResponse)

        every { getLocationByIdService.getLocationById(idLocation) } returns locationDTO

        mockMvc.perform(
            get("/locations/{idLocation}", idLocation)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson))
    }

    @Test
    fun shouldReturn404WhenIdDoesNotExist() {

        val idLocation = 999L

        every { getLocationByIdService.getLocationById(idLocation) } throws LocationNotFoundException(idLocation)

        mockMvc.perform(
            get("/locations/{idLocation}", idLocation)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }
}
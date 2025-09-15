package com.api.getcep.integrationTest.controllers

import com.api.getcep.controllers.UpdateLocationController
import com.api.getcep.controllers.request.UpdateLocationRequest
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.dtos.UpdateLocationDTO
import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.mappers.toLocationResponse
import com.api.getcep.services.UpdateLocationService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UpdateLocationController::class)
class UpdateLocationControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val updateLocationService: UpdateLocationService,
    private val objectMapper: ObjectMapper
) {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun updateLocationService() = mockk<UpdateLocationService>()
    }

    @Test
    fun shouldReturn200AndUpdatedLocationWhenLocationExists() {
        val idLocation = 1L
        val updateLocationRequest = UpdateLocationRequest(
            logradouro = "Praça da Sé - lado par",
            complemento = "lado par",
            unidade = null,
            bairro = "Sé",
            localidade = "São Paulo",
            regiao = "Sudeste",
            ibge = "3550308",
            gia = "1004",
            siafi = "7107"
        )
        val updatedLocationDTO = LocationDTO(
            idLocation = idLocation,
            cep = "01001-001",
            logradouro = "Praça da Sé - lado par",
            complemento = "lado par",
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
        val expectedResponse = updatedLocationDTO.toLocationResponse()
        val jsonRequest = objectMapper.writeValueAsString(updateLocationRequest)

        every { updateLocationService.updateLocation(eq(idLocation), any<UpdateLocationDTO>()) } returns updatedLocationDTO

        mockMvc.perform(
            put("/locations/{idLocation}", idLocation)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)))
    }

    @Test
    fun shouldReturn404WhenLocationToUpdateDoesNotExist() {
        val idLocation = 999L
        val updateLocationRequest = UpdateLocationRequest(
            logradouro = "Praça da Sé - lado par",
            complemento = "lado par",
            unidade = null,
            bairro = "Sé",
            localidade = "São Paulo",
            regiao = "Sudeste",
            ibge = "3550308",
            gia = "1004",
            siafi = "7107"
        )
        val jsonRequest = objectMapper.writeValueAsString(updateLocationRequest)

        every { updateLocationService.updateLocation(eq(idLocation), any<UpdateLocationDTO>()) } throws LocationNotFoundException(
            idLocation
        )

        mockMvc.perform(
            put("/locations/{idLocation}", idLocation)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(status().isNotFound)
    }
}
package com.api.getcep.integrationTest.controllers

import com.api.getcep.controllers.SaveLocationByApiCepController
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.exceptions.CepAlreadyExistsException
import com.api.getcep.exceptions.CepNotFoundException
import com.api.getcep.exceptions.InvalidCepFormatException
import com.api.getcep.mappers.toLocationResponse
import com.api.getcep.services.SaveLocationByCepService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(SaveLocationByApiCepController::class)
class SaveLocationByApiCepControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val saveLocationByCepService: SaveLocationByCepService,
    private val objectMapper: ObjectMapper
) {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun saveLocationByCepService() = mockk<SaveLocationByCepService>()
    }

    @Test
    fun shouldReturn201AndLocationResponseWhenCepIsSuccessfullySaved() {
        val cep = "01001-000"
        val locationDTO = LocationDTO(
            idLocation = 1L,
            cep = cep,
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

        every { saveLocationByCepService.saveLocationByCep(cep) } returns locationDTO

        mockMvc.perform(
            post("/locations/{cep}", cep)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(locationResponse)))
    }

    @Test
    fun shouldReturn409WhenCepAlreadyExists() {
        val cep = "01001-000"

        every { saveLocationByCepService.saveLocationByCep(cep) } throws CepAlreadyExistsException(cep)

        mockMvc.perform(
            post("/locations/{cep}", cep)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isConflict)
    }

    @Test
    fun shouldReturn400WhenCepFormatIsInvalid() {
        val cep = "cep-invalido"

        every { saveLocationByCepService.saveLocationByCep(cep) } throws InvalidCepFormatException(cep)

        mockMvc.perform(
            post("/locations/{cep}", cep)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun shouldReturn404WhenCepIsNotFound() {
        val cep = "99999-999"

        every { saveLocationByCepService.saveLocationByCep(cep) } throws CepNotFoundException(cep)

        mockMvc.perform(
            post("/locations/{cep}", cep)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }
}
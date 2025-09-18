package com.api.getcep.integrationTest.controllers

import com.api.getcep.controllers.request.UpdateLocationRequest
import com.api.getcep.integrationTest.builder.LocationBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.api.getcep.integrationTest.mock.BaseIntegrationTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UpdateLocationControllerTest : BaseIntegrationTest() {

    private lateinit var builder: LocationBuilder

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
        builder = LocationBuilder(objectMapper)
    }

    @Test
    fun shouldReturn200AndUpdatedLocationWhenLocationExists() {
        val savedLocation = locationRepository.save(
            builder.create(
                cep = "01001-000",
                logradouro = "Praça da Sé",
                bairro = "Sé",
                localidade = "São Paulo",
                uf = "SP"
            )
        )

        val updateRequest = UpdateLocationRequest(
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

        val jsonRequest = objectMapper.writeValueAsString(updateRequest)

        val expectedLocation = savedLocation.copy(
            logradouro = updateRequest.logradouro,
            complemento = updateRequest.complemento,
            unidade = updateRequest.unidade,
            bairro = updateRequest.bairro,
            localidade = updateRequest.localidade,
            regiao = updateRequest.regiao,
            ibge = updateRequest.ibge,
            gia = updateRequest.gia,
            siafi = updateRequest.siafi
        )

        mockMvc.perform(
            put("/locations/{idLocation}", savedLocation.idLocation!!)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(builder.toJson(expectedLocation)))

        val updatedLocation = locationRepository.findById(savedLocation.idLocation!!).get()
        assert(updatedLocation.logradouro == updateRequest.logradouro)
    }

    @Test
    fun shouldReturn404WhenLocationToUpdateDoesNotExist() {
        val nonExistentId = 999L

        val updateRequest = UpdateLocationRequest(
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

        val jsonRequest = objectMapper.writeValueAsString(updateRequest)

        mockMvc.perform(
            put("/locations/{idLocation}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(status().isNotFound)
    }
}


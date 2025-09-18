package com.api.getcep.integrationTest.controllers

import com.api.getcep.integrationTest.builder.LocationBuilder
import org.junit.jupiter.api.Test
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import com.api.getcep.integrationTest.mock.BaseIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.BeforeEach
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureWireMock(port = 0)
class SaveLocationByApiCepControllerTest: BaseIntegrationTest() {

    private lateinit var builder: LocationBuilder

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
        builder = LocationBuilder(objectMapper)
    }

    @Test
    fun shouldReturn201AndLocationResponseWhenCepIsSuccessfullySaved() {
        val cep = "01001-000"

        WireMock.stubFor(
            WireMock.get(WireMock.urlEqualTo("/$cep/json/"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(builder.getApiCepJson(
                            cep = cep,
                            logradouro = "Praça da Sé",
                            complemento = "lado ímpar",
                            bairro = "Sé",
                            localidade = "São Paulo",
                            uf = "SP",
                            ibge = "3550308",
                            gia = "1004",
                            ddd = "11",
                            siafi = "7107"
                        ))
                )
        )

        mockMvc.perform(
            post("/locations/{cep}", cep)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(builder.toJson(locationRepository.findAll().first())))
    }

    @Test
    fun shouldReturn409WhenCepAlreadyExists() {
        val cep = "01001-000"
        locationRepository.save(builder.create(cep = cep))

        mockMvc.perform(
            post("/locations/{cep}", cep)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isConflict)
    }

    @Test
    fun shouldReturn400WhenCepFormatIsInvalid() {
        val invalidCep = "cep-invalido"

        mockMvc.perform(
            post("/locations/{cep}", invalidCep)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun shouldReturn404WhenCepIsNotFound() {
        val cepNotFound = "99999-999"

        WireMock.stubFor(
            WireMock.get(WireMock.urlEqualTo("/$cepNotFound/json/"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""{"erro": true}""")
                )
        )

        mockMvc.perform(
            post("/locations/{cep}", cepNotFound)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }
}
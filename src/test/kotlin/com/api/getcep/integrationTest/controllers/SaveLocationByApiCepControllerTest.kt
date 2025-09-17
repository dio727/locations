package com.api.getcep.integrationTest.controllers

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.mappers.toLocationResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import com.api.getcep.Application
import com.api.getcep.domain.location.repositories.LocationRepository
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.BeforeEach
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
@SpringBootTest(classes = [Application::class])
@ActiveProfiles("test")
class SaveLocationByApiCepControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val locationRepository: LocationRepository,
    private val objectMapper: ObjectMapper
) {

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
    }

    @Test
    fun shouldReturn201AndLocationResponseWhenCepIsSuccessfullySaved() {
        val cep = "01001-000"

        WireMock.stubFor(
            WireMock.get(WireMock.urlEqualTo("/$cep/json/"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "cep": "01001-000",
                                "logradouro": "Praça da Sé",
                                "complemento": "lado ímpar",
                                "bairro": "Sé",
                                "localidade": "São Paulo",
                                "uf": "SP",
                                "ibge": "3550308",
                                "gia": "1004",
                                "ddd": "11",
                                "siafi": "7107"
                            }
                        """.trimIndent())
                )
        )

        mockMvc.perform(
            post("/locations/{cep}", cep)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(
                locationRepository.findAll().first().toDTO().toLocationResponse()
            )))
    }

    @Test
    fun shouldReturn409WhenCepAlreadyExists() {
        val cep = "01001-000"
        locationRepository.save(createLocation(cep))

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

    private fun createLocation(cep: String): LocationEntity {
        return LocationEntity(
            idLocation = null,
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
    }

    private fun LocationEntity.toDTO(): LocationDTO {
        return LocationDTO(
            idLocation = this.idLocation,
            cep = this.cep,
            logradouro = this.logradouro,
            complemento = this.complemento,
            unidade = this.unidade,
            bairro = this.bairro,
            localidade = this.localidade,
            uf = this.uf,
            estado = this.estado,
            regiao = this.regiao,
            ibge = this.ibge,
            gia = this.gia,
            ddd = this.ddd,
            siafi = this.siafi
        )
    }
}
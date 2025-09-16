package com.api.getcep.integrationTest.controllers

import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.mappers.toLocationResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import com.api.getcep.Application
import com.api.getcep.domain.location.repositories.LocationRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(classes = [Application::class])
@ActiveProfiles("test")
class GetLocationByIdControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val locationRepository: LocationRepository,
    private val objectMapper: ObjectMapper
) {

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
    }

    @Test
    fun shouldReturn200AndLocationWhenIdExists() {
        val savedLocation = locationRepository.save(
            createLocation(
                cep = "01001-000",
                logradouro = "Praça da Sé",
                bairro = "Sé",
                localidade = "São Paulo",
                uf = "SP"
            )
        )

        val expectedJson = objectMapper.writeValueAsString(savedLocation.toDTO().toLocationResponse())

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

    private fun createLocation(cep: String, logradouro: String, bairro: String, localidade: String, uf: String): LocationEntity {
        return LocationEntity(
            idLocation = null,
            cep = cep,
            logradouro = logradouro,
            complemento = null,
            unidade = null,
            bairro = bairro,
            localidade = localidade,
            uf = uf,
            estado = localidade,
            regiao = "Sudeste",
            ibge = "0000000",
            gia = null,
            ddd = "00",
            siafi = "0000"
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

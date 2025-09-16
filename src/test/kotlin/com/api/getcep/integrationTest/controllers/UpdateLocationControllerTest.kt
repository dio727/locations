package com.api.getcep.integrationTest.controllers

import com.api.getcep.controllers.request.UpdateLocationRequest
import com.api.getcep.domain.location.entities.LocationEntity
import com.api.getcep.domain.location.repositories.LocationRepository
import com.api.getcep.dtos.LocationDTO
import com.api.getcep.mappers.toLocationResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.api.getcep.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(classes = [Application::class])
@ActiveProfiles("test")
class UpdateLocationControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val locationRepository: LocationRepository,
    private val objectMapper: ObjectMapper
) {

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
    }

    @Test
    fun shouldReturn200AndUpdatedLocationWhenLocationExists() {
        val savedLocation = locationRepository.save(
            createLocation(
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
            .andExpect(content().json(objectMapper.writeValueAsString(expectedLocation.toDTO().toLocationResponse())))

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

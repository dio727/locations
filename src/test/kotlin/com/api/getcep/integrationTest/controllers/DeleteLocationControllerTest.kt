package com.api.getcep.integrationTest.controllers

import com.api.getcep.integrationTest.builder.LocationBuilder
import com.api.getcep.integrationTest.mock.BaseIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

class DeleteLocationControllerTest : BaseIntegrationTest() {

    private lateinit var builder: LocationBuilder

    @BeforeEach
    fun setup() {
        locationRepository.deleteAll()
        builder = LocationBuilder(objectMapper)
    }

    @Test
    fun shouldReturn204NoContentWhenLocationIsSuccessfullyDeleted() {
        val savedLocation = locationRepository.save(builder.create())

        mockMvc.perform(
            delete("/locations/{idLocation}", savedLocation.idLocation!!)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun shouldReturn404NotFoundWhenLocationDoesNotExist() {
        mockMvc.perform(
            delete("/locations/{idLocation}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }
}
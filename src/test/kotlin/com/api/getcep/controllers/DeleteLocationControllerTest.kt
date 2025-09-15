package com.api.getcep.controllers

import com.api.getcep.exceptions.LocationNotFoundException
import com.api.getcep.services.DeleteLocationService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DeleteLocationController::class)
class DeleteLocationControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val deleteLocationService: DeleteLocationService,
    private val objectMapper: ObjectMapper
) {
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun deleteLocationService() = mockk<DeleteLocationService>()
    }

    @Test
    fun shouldReturn204NoContentWhenLocationIsSuccessfullyDeleted() {
        val idLocation = 1L
        every { deleteLocationService.deleteLocation(idLocation) } returns Unit

        mockMvc.perform(
            delete("/locations/{idLocation}", idLocation)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)

        verify(exactly = 1) { deleteLocationService.deleteLocation(idLocation) }
    }

    @Test
    fun shouldReturn404NotFoundWhenLocationDoesNotExist() {
        val idLocation = 999L
        every { deleteLocationService.deleteLocation(idLocation) } throws LocationNotFoundException(idLocation)

        mockMvc.perform(
            delete("/locations/{idLocation}", idLocation)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)

        verify(exactly = 1) { deleteLocationService.deleteLocation(idLocation) }
    }
}
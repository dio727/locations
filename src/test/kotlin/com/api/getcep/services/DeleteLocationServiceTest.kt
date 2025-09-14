package com.api.getcep.services

import com.api.getcep.domain.location.LocationEntity
import com.api.getcep.domain.location.LocationRepository
import java.util.Optional
import kotlin.test.Test
import io.mockk.every

class DeleteLocationServiceTest {
    private val locationRepository: LocationRepository = mockk()
    private val deleteLocationService = DeleteLocationService(locationRepository)

    @Test
    fun `deve deletar location quando encontrada`() {
        val location = LocationEntity(idLocation = 1L, cep = "01001-000", logradouro = "Rua Teste", complemento = "")
        every { locationRepository.findById(1L) } returns Optional.of(location)
        justRun { locationRepository.delete(location) }

        deleteLocationService.deleteLocation(1L)

        verify(exactly = 1) { locationRepository.delete(location) }
    }

    @Test
    fun `deve lançar LocationNotFoundException quando location não encontrada`() {
        every { locationRepository.findById(99L) } returns Optional.empty()

        assertThrows(LocationNotFoundException::class.java) {
            deleteLocationService.deleteLocation(99L)
        }

        verify(exactly = 0) { locationRepository.delete(any()) }
    }
}
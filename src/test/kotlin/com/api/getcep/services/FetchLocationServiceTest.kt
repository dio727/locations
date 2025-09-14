package com.api.getcep.services

import com.api.getcep.client.GetLocationClient
import com.api.getcep.controllers.response.LocationResponse
import com.api.getcep.dtos.LocationDTO
import kotlin.test.Test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows

class FetchLocationServiceTest {
    private val getLocationClient: GetLocationClient = mockk()
    private val fetchLocationService = FetchLocationService(getLocationClient)

//    @Test
//    fun shouldReturnLocationDTOWhenCepIsValid() {
//        val cep = "01001-000"
//        val locationResponse = mockk<LocationResponse>()
//        val locationDTO = LocationDTO(
//            idLocation = null,
//            cep = "01001-000",
//            logradouro = "Rua Teste",
//            complemento = "",
//            unidade = null,
//            bairro = "Bairro Teste",
//            localidade = "São Paulo",
//            uf = "SP",
//            estado = "São Paulo",
//            regiao = "Sudeste",
//            ibge = "3550308",
//            gia = "1004",
//            ddd = "11",
//            siafi = "7107"
//        )
//
//        every { locationResponse.toLocationDTO() } returns locationDTO
//        every { getLocationClient.getLocationByCep(cep) } returns locationResponse
//
//        val result = fetchLocationService.fetchByCep(cep)
//
//        assertEquals(locationDTO, result)
//        verify(exactly = 1) { getLocationClient.getLocationByCep(cep) }
//    }
//
//    @Test
//    fun shouldThrowInvalidCepFormatExceptionWhenBadRequest() {
//        val cep = "INVALID"
//        every { getLocationClient.getLocationByCep(cep) } throws FeignException.BadRequest("Bad Request", null, null)
//
//        assertThrows<InvalidCepFormatException> {
//            fetchLocationService.fetchByCep(cep)
//        }
//
//        verify(exactly = 1) { getLocationClient.getLocationByCep(cep) }
//    }
//
//    @Test
//    fun shouldThrowCepNotFoundExceptionWhenNotFound() {
//        val cep = "99999-999"
//        every { getLocationClient.getLocationByCep(cep) } throws FeignException.NotFound("Not Found", null, null)
//
//        assertThrows<CepNotFoundException> {
//            fetchLocationService.fetchByCep(cep)
//        }
//
//        verify(exactly = 1) { getLocationClient.getLocationByCep(cep) }
//    }
}
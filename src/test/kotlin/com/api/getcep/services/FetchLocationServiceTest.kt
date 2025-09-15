package com.api.getcep.services

import com.api.getcep.client.GetLocationClient
import com.api.getcep.client.response.ApiCepLocationResponse
import com.api.getcep.mappers.toLocationDTO
import kotlin.test.Test
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals

private val getLocationClient: GetLocationClient = mockk()
private val fetchLocationService = FetchLocationService(getLocationClient)
class FetchLocationServiceTest {

    @Test
    fun shouldReturnLocationDTOWhenCepIsFound() {
        val cep = "01001-000"
        val apiResponse = ApiCepLocationResponse(
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
        val expectedDTO = apiResponse.toLocationDTO()

        every { getLocationClient.getLocationByCep(cep) } returns apiResponse

        val result = fetchLocationService.fetchByCep(cep)

        assertEquals(expectedDTO, result)
    }
}
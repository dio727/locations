package com.api.getcep.unitTests.services

import com.api.getcep.integrations.client.GetLocationClient
import com.api.getcep.integrations.client.response.ApiCepLocationResponse
import com.api.getcep.exceptions.CepNotFoundException
import com.api.getcep.exceptions.InvalidCepFormatException
import com.api.getcep.mappers.toLocationDTO
import com.api.getcep.services.FetchLocationService
import com.api.getcep.unitTests.services.mock.BaseServiceTest
import feign.FeignException
import feign.Request
import kotlin.test.Test
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.nio.charset.Charset
import kotlin.test.assertEquals

class FetchLocationServiceTest : BaseServiceTest() {

    private lateinit var fetchLocationService: FetchLocationService

    @BeforeEach
    fun setup() {
        fetchLocationService = FetchLocationService(getLocationClient)
    }

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
            siafi = "7107",
            erro = null
        )
        val expectedDTO = apiResponse.toLocationDTO()

        every { getLocationClient.getLocationByCep(cep) } returns apiResponse

        val result = fetchLocationService.fetchByCep(cep)

        assertEquals(expectedDTO, result)
    }

    @Test
    fun shouldThrowInvalidCepFormatExceptionWhenCepIsInvalid() {
        val cep = "cep-invalido"

        every { getLocationClient.getLocationByCep(cep) } throws FeignException.BadRequest(
            "Bad Request",
            Request.create(Request.HttpMethod.GET, "/", mapOf(), null, Charset.defaultCharset(), null),
            null,
            mapOf()
        )

        assertThrows<InvalidCepFormatException> {
            fetchLocationService.fetchByCep(cep)
        }
    }

    @Test
    fun shouldThrowCepNotFoundExceptionWhenCepIsNotFound() {
        val cep = "99999-999"

        every { getLocationClient.getLocationByCep(cep) } returns ApiCepLocationResponse(
            cep = "", logradouro = null, complemento = null, bairro = null,
            localidade = null, uf = null, ibge = null, gia = null, ddd = null,
            siafi = null, regiao = null, estado = null, unidade = null, erro = true
        )

        assertThrows<CepNotFoundException> {
            fetchLocationService.fetchByCep(cep)
        }
    }
}

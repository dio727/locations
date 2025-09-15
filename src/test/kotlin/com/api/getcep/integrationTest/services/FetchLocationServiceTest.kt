package com.api.getcep.integrationTest.services

import com.api.getcep.integrations.client.GetLocationClient
import com.api.getcep.integrations.client.response.ApiCepLocationResponse
import com.api.getcep.exceptions.CepNotFoundException
import com.api.getcep.exceptions.InvalidCepFormatException
import com.api.getcep.services.FetchLocationService
import com.ninjasquad.springmockk.MockkBean
import feign.FeignException
import feign.Request
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.nio.charset.Charset

@SpringBootTest
@ExtendWith(MockKExtension::class)
class FetchLocationServiceTest {

    @Autowired
    lateinit var fetchLocationService: FetchLocationService

    @MockkBean
    lateinit var getLocationClient: GetLocationClient

    @Test
    fun shouldReturnLocationDTOWhenClientReturnsSuccess() {
        val cep = "01001-000"
        val clientResponse = ApiCepLocationResponse(
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

        every { getLocationClient.getLocationByCep(cep) } returns clientResponse

        val result = fetchLocationService.fetchByCep(cep)

        assertNotNull(result)
        assertEquals(cep, result.cep)
        assertEquals("Praça da Sé", result.logradouro)
        assertEquals("São Paulo", result.localidade)
    }

    @Test
    fun shouldThrowInvalidCepFormatExceptionWhenClientReturns400BadRequest() {
        val cep = "cep-invalido"
        val request = Request.create(Request.HttpMethod.GET, "/api", emptyMap(), null, Charset.defaultCharset(), null)

        every { getLocationClient.getLocationByCep(cep) } throws FeignException.BadRequest("Bad Request", request, null, emptyMap())

        assertThrows<InvalidCepFormatException> {
            fetchLocationService.fetchByCep(cep)
        }
    }

    @Test
    fun shouldThrowCepNotFoundExceptionWhenClientReturns404NotFound() {
        val cep = "99999-999"
        val request = Request.create(Request.HttpMethod.GET, "/api", emptyMap(), null, Charset.defaultCharset(), null)

        every { getLocationClient.getLocationByCep(cep) } throws FeignException.NotFound("Not Found", request, null, emptyMap())

        assertThrows<CepNotFoundException> {
            fetchLocationService.fetchByCep(cep)
        }
    }
}
package com.api.getcep.unitTests.domain.location

import com.api.getcep.domain.location.entities.LocationEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LocationEntityTest {

    private fun createCompleteLocationEntity(id: Long?) = LocationEntity(
        idLocation = id,
        cep = "01001-000",
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

    @Test
    fun shouldCreateLocationEntityWithCorrectFields() {
        val location = createCompleteLocationEntity(1L)

        Assertions.assertEquals(1L, location.idLocation)
        Assertions.assertEquals("01001-000", location.cep)
        Assertions.assertEquals("Praça da Sé", location.logradouro)
        Assertions.assertEquals("lado ímpar", location.complemento)
        Assertions.assertNull(location.unidade)
        Assertions.assertEquals("Sé", location.bairro)
        Assertions.assertEquals("São Paulo", location.localidade)
        Assertions.assertEquals("SP", location.uf)
        Assertions.assertEquals("São Paulo", location.estado)
        Assertions.assertEquals("Sudeste", location.regiao)
        Assertions.assertEquals("3550308", location.ibge)
        Assertions.assertEquals("1004", location.gia)
        Assertions.assertEquals("11", location.ddd)
        Assertions.assertEquals("7107", location.siafi)
    }

    @Test
    fun shouldUpdateMutableFields() {
        val location = createCompleteLocationEntity(1L)

        location.logradouro = "Rua Teste"
        location.complemento = "Apto 101"
        location.unidade = "Teste Unidade"
        location.bairro = "Novo Bairro"
        location.localidade = "Nova Localidade"
        location.ibge = "1234567"
        location.gia = "7890"
        location.siafi = "4321"

        Assertions.assertEquals("Rua Teste", location.logradouro)
        Assertions.assertEquals("Apto 101", location.complemento)
        Assertions.assertEquals("Teste Unidade", location.unidade)
        Assertions.assertEquals("Novo Bairro", location.bairro)
        Assertions.assertEquals("Nova Localidade", location.localidade)
        Assertions.assertEquals("1234567", location.ibge)
        Assertions.assertEquals("7890", location.gia)
        Assertions.assertEquals("4321", location.siafi)
    }

    @Test
    fun shouldHandleEqualityCorrectly() {
        val location1 = createCompleteLocationEntity(1L)
        val location2 = createCompleteLocationEntity(1L)
        val location3 = createCompleteLocationEntity(2L)

        Assertions.assertEquals(location1, location2)
        Assertions.assertNotEquals(location1, location3)
    }

    @Test
    fun shouldHaveSameHashCodeForEqualObjects() {
        val location1 = createCompleteLocationEntity(1L)
        val location2 = createCompleteLocationEntity(1L)

        Assertions.assertEquals(location1.hashCode(), location2.hashCode())
    }

    @Test
    fun shouldCopyObjectCorrectly() {
        val original = createCompleteLocationEntity(1L)
        val copy = original.copy()

        Assertions.assertEquals(original, copy)
        Assertions.assertNotSame(original, copy)
    }

    @Test
    fun shouldHandleDefaultConstructorWithNullId() {
        val location = LocationEntity(
            cep = "01001-000",
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
        Assertions.assertNull(location.idLocation)
        Assertions.assertEquals("01001-000", location.cep)
        Assertions.assertEquals("Praça da Sé", location.logradouro)
        Assertions.assertEquals("lado ímpar", location.complemento)
        Assertions.assertNull(location.unidade)
        Assertions.assertEquals("Sé", location.bairro)
        Assertions.assertEquals("São Paulo", location.localidade)
        Assertions.assertEquals("SP", location.uf)
        Assertions.assertEquals("São Paulo", location.estado)
        Assertions.assertEquals("Sudeste", location.regiao)
        Assertions.assertEquals("3550308", location.ibge)
        Assertions.assertEquals("1004", location.gia)
        Assertions.assertEquals("11", location.ddd)
        Assertions.assertEquals("7107", location.siafi)
    }

    @Test
    fun shouldReturnCorrectStringRepresentation() {
        val location = createCompleteLocationEntity(1L)
        val expectedString = "LocationEntity(idLocation=1, cep=01001-000, logradouro=Praça da Sé, complemento=lado ímpar, unidade=null, bairro=Sé, localidade=São Paulo, uf=SP, estado=São Paulo, regiao=Sudeste, ibge=3550308, gia=1004, ddd=11, siafi=7107)"
        Assertions.assertEquals(expectedString, location.toString())
    }
}
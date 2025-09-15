package com.api.getcep.domain.location

import com.api.getcep.domain.location.entities.LocationEntity
import org.junit.jupiter.api.Assertions.*
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

        assertEquals(1L, location.idLocation)
        assertEquals("01001-000", location.cep)
        assertEquals("Praça da Sé", location.logradouro)
        assertEquals("lado ímpar", location.complemento)
        assertNull(location.unidade)
        assertEquals("Sé", location.bairro)
        assertEquals("São Paulo", location.localidade)
        assertEquals("SP", location.uf)
        assertEquals("São Paulo", location.estado)
        assertEquals("Sudeste", location.regiao)
        assertEquals("3550308", location.ibge)
        assertEquals("1004", location.gia)
        assertEquals("11", location.ddd)
        assertEquals("7107", location.siafi)
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

        assertEquals("Rua Teste", location.logradouro)
        assertEquals("Apto 101", location.complemento)
        assertEquals("Teste Unidade", location.unidade)
        assertEquals("Novo Bairro", location.bairro)
        assertEquals("Nova Localidade", location.localidade)
        assertEquals("1234567", location.ibge)
        assertEquals("7890", location.gia)
        assertEquals("4321", location.siafi)
    }

    @Test
    fun shouldHandleEqualityCorrectly() {
        val location1 = createCompleteLocationEntity(1L)
        val location2 = createCompleteLocationEntity(1L)
        val location3 = createCompleteLocationEntity(2L)

        assertEquals(location1, location2)
        assertNotEquals(location1, location3)
    }

    @Test
    fun shouldHaveSameHashCodeForEqualObjects() {
        val location1 = createCompleteLocationEntity(1L)
        val location2 = createCompleteLocationEntity(1L)

        assertEquals(location1.hashCode(), location2.hashCode())
    }

    @Test
    fun shouldCopyObjectCorrectly() {
        val original = createCompleteLocationEntity(1L)
        val copy = original.copy()

        assertEquals(original, copy)
        assertNotSame(original, copy)
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
        assertNull(location.idLocation)
        assertEquals("01001-000", location.cep)
        assertEquals("Praça da Sé", location.logradouro)
        assertEquals("lado ímpar", location.complemento)
        assertNull(location.unidade)
        assertEquals("Sé", location.bairro)
        assertEquals("São Paulo", location.localidade)
        assertEquals("SP", location.uf)
        assertEquals("São Paulo", location.estado)
        assertEquals("Sudeste", location.regiao)
        assertEquals("3550308", location.ibge)
        assertEquals("1004", location.gia)
        assertEquals("11", location.ddd)
        assertEquals("7107", location.siafi)
    }

    @Test
    fun shouldReturnCorrectStringRepresentation() {
        val location = createCompleteLocationEntity(1L)
        val expectedString = "LocationEntity(idLocation=1, cep=01001-000, logradouro=Praça da Sé, complemento=lado ímpar, unidade=null, bairro=Sé, localidade=São Paulo, uf=SP, estado=São Paulo, regiao=Sudeste, ibge=3550308, gia=1004, ddd=11, siafi=7107)"
        assertEquals(expectedString, location.toString())
    }
}
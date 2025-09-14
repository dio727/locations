package com.api.getcep.client

import com.api.getcep.client.response.ApiCepLocationResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
interface GetLocationClient {
    @GetMapping("/{cep}/json/")
    fun getLocationByCep(@PathVariable("cep") cep: String): ApiCepLocationResponse
}
package com.api.getcep.integrations.client

import com.api.getcep.integrations.client.response.ApiCepLocationResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "viacep", url = "\${viacep.url}")
interface GetLocationClient {
    @GetMapping("/{cep}/json/")
    fun getLocationByCep(@PathVariable("cep") cep: String): ApiCepLocationResponse
}
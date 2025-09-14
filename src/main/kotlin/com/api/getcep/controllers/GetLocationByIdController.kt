package com.api.getcep.controllers

import com.api.getcep.controllers.response.LocationResponse
import com.api.getcep.mappers.toLocationResponse
import com.api.getcep.services.GetLocationByIdService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/locations")
class GetLocationByIdController(private val getLocationByIdService: GetLocationByIdService) {

    @Operation(
        summary = "Busca uma Location pelo ID",
        description = "Retorna os detalhes de uma Location específica pelo seu ID. Retorna 404 se não encontrada."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Location encontrada com sucesso",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = LocationResponse::class))]
        ),
        ApiResponse(responseCode = "404", description = "Location não encontrada",
            content = [Content(schema = Schema())]
        )
    )
    @GetMapping("/{idLocation}")
    fun getLocationById(@PathVariable idLocation: Long): ResponseEntity<LocationResponse> {
        val location = getLocationByIdService.getLocationById(idLocation).toLocationResponse()
        return ResponseEntity.status(HttpStatus.OK).body(location)
    }
}
package com.api.getcep.controllers

import com.api.getcep.controllers.response.LocationResponse
import com.api.getcep.controllers.request.UpdateLocationRequest
import com.api.getcep.mappers.toLocationResponse
import com.api.getcep.mappers.toUpdateLocationDTO
import com.api.getcep.services.UpdateLocationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("locations")
class UpdateLocationController(private val updateLocationService: UpdateLocationService) {

    @Operation(
        summary = "Atualiza uma Location pelo ID",
        description = "Atualiza os dados de uma Location existente. Retorna 404 se a Location não for encontrada."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Location atualizada com sucesso",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = LocationResponse::class))]
        ),
        ApiResponse(responseCode = "404", description = "Location não encontrada",
            content = [Content(schema = Schema())]
        )
    )
    @PutMapping("/{idLocation}")
    fun updateLocation(
        @PathVariable idLocation: Long,
        @RequestBody updateLocationRequest: UpdateLocationRequest
    ): ResponseEntity<LocationResponse> {
        val dto = updateLocationRequest.toUpdateLocationDTO()
        val updated = updateLocationService.updateLocation(idLocation, dto).toLocationResponse()
        return ResponseEntity.ok(updated)
    }
}
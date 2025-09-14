package com.api.getcep.controllers

import com.api.getcep.controllers.response.LocationResponse
import com.api.getcep.mappers.toLocationResponse
import com.api.getcep.services.GetAllLocationsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/locations")
class GetAllLocationsController(private val getAllLocationsService: GetAllLocationsService) {

    @Operation(
        summary = "Lista todas as Locations",
        description = "Retorna uma lista de todas as Locations cadastradas no sistema."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lista de Locations retornada com sucesso")
    )
    @GetMapping()
    fun getAllLocations(): ResponseEntity<List<LocationResponse>> {
        val locations = getAllLocationsService.getAllLocations().map {
            it.toLocationResponse()
        }
        return ResponseEntity.status(HttpStatus.OK).body(locations)
    }
}
package com.api.getcep.controllers

import com.api.getcep.services.DeleteLocationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/locations")
class DeleteLocationController(private val deleteLocationService: DeleteLocationService) {

    @Operation(
        summary = "Deleta uma Location pelo ID",
        description = "Remove uma Location existente. Retorna 404 se o recurso não for encontrado."
    )
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Location deletada com sucesso"),
        ApiResponse(responseCode = "404", description = "Location não encontrada")
    )
    @DeleteMapping("/{idLocation}")
    fun deleteLocation(@PathVariable idLocation: Long): ResponseEntity<Void> {
        deleteLocationService.deleteLocation(idLocation)
        return ResponseEntity.noContent().build()
    }
}
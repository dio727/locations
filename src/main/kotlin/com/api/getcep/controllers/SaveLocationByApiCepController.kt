package com.api.getcep.controllers

import com.api.getcep.controllers.response.LocationResponse
import com.api.getcep.mappers.toLocationResponse
import com.api.getcep.services.SaveLocationByCepService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema


@RestController
@RequestMapping("locations")
class SaveLocationByApiCepController(private val saveLocationByCepService: SaveLocationByCepService) {

    @Operation(
        summary = "Cria uma Location pelo CEP",
        description = "Salva uma nova Location a partir do CEP fornecido. Retorna 201 se criada com sucesso."
    )
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Location criada com sucesso",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = LocationResponse::class))]
        ),
        ApiResponse(responseCode = "409", description = "Não permite salvar uma Location com CEP já existente",
            content = [Content(schema = Schema())] // vazio
        ),
        ApiResponse(responseCode = "400", description = "Formato de CEP inválido",
            content = [Content(schema = Schema())]
        ),
        ApiResponse(responseCode = "404", description = "CEP não encontrado",
            content = [Content(schema = Schema())]
        )
    )

    @PostMapping("/{cep}")
    fun saveLocationByCep(@PathVariable cep: String): ResponseEntity<LocationResponse> {
        val locationResponse = saveLocationByCepService.saveLocationByCep(cep).toLocationResponse()

        return ResponseEntity.status(HttpStatus.CREATED).body(locationResponse)
    }
}
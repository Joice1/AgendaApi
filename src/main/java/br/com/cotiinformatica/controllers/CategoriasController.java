package br.com.cotiinformatica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.repositories.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorias", description = "Endpoint para operações relacionadas a categorias de tarefas")
public class CategoriasController {

    @Operation(
        summary = "Consultar todas as categorias",
        description = "Retorna uma lista com todas as categorias de tarefas cadastradas no sistema"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Consulta realizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Object.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content = @Content(schema = @Schema(implementation = String.class))
        )
    })
    @GetMapping
    public ResponseEntity<?> get() {
        try {
            
            var categoriaRepository = new CategoriaRepository();
            var lista = categoriaRepository.findAll();
            
            return ResponseEntity.ok() //HTTP 200 (OK)
                    .body(lista); //lista de categorias
        }
        catch(Exception e) {            
            return ResponseEntity.internalServerError() //HTTP 500 (Internal Server Error)
                    .body(e.getMessage()); //mensagem de erro
        }
    }
}
package br.com.cotiinformatica.controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.TarefaPostRequest;
import br.com.cotiinformatica.dtos.TarefaPutRequest;
import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.entities.Tarefa;
import br.com.cotiinformatica.enums.Prioridade;
import br.com.cotiinformatica.repositories.TarefaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tarefas")
@Tag(name = "Tarefas", description = "Endpoint para operações relacionadas a tarefas")
public class TarefasController {

	@Operation(
	    summary = "Cadastrar nova tarefa",
	    description = "Cria uma nova tarefa no sistema com os dados fornecidos"
	)
	@PostMapping
	public ResponseEntity<?> post(
	        @Parameter(
	            description = "Dados da tarefa a ser criada",
	            required = true,
	            content = @Content(schema = @Schema(implementation = TarefaPostRequest.class))
	        )
	        @RequestBody TarefaPostRequest request) {

		try {
			
			//Capturar os dados da requisição
			var tarefa = new Tarefa();
			
			tarefa.setId(UUID.randomUUID());
			tarefa.setNome(request.getNome());
			tarefa.setData(LocalDate.parse(request.getData()));
			tarefa.setPrioridade(Prioridade.valueOf(request.getPrioridade()));
			tarefa.setFinalizado(false);
			
			tarefa.setCategoria(new Categoria());
			tarefa.getCategoria().setId(UUID.fromString(request.getCategoriaId()));
			
			//Salvar no banco de dados
			var tarefaRepository = new TarefaRepository();
			tarefaRepository.insert(tarefa);
			
			//HTTP 201 (Created)
			return ResponseEntity.status(201).body("Tarefa cadastrada com sucesso!");
		}
		catch(Exception e) {
			//HTTP 500 (Internal Server Error)
			return ResponseEntity.internalServerError()
					.body(e.getMessage());
		}
	}
	
	@Operation(
	    summary = "Atualizar tarefa existente",
	    description = "Atualiza os dados de uma tarefa existente no sistema"
	)
	@PutMapping("{id}")
	public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody TarefaPutRequest request) {
		
		try {
			
			//Capturar os dados da requisição
			var tarefa = new Tarefa();
			
			tarefa.setId(id);
			tarefa.setNome(request.getNome());
			tarefa.setData(LocalDate.parse(request.getData()));
			tarefa.setPrioridade(Prioridade.valueOf(request.getPrioridade()));
			tarefa.setFinalizado(request.getFinalizado());
			
			tarefa.setCategoria(new Categoria());
			tarefa.getCategoria().setId(UUID.fromString(request.getCategoriaId()));
			
			//Salvar no banco de dados
			var tarefaRepository = new TarefaRepository();
			
			if(tarefaRepository.update(tarefa)) {
				return ResponseEntity.status(200).body("Tarefa atualizada com sucesso.");
			}
			else {
				return ResponseEntity.status(404).body("Tarefa não encontrada. Verifique o ID informado.");
			}
		}
		catch(Exception e) {
			//HTTP 500 (Internal Server Error)
			return ResponseEntity.internalServerError()
					.body(e.getMessage());
		}
	}
	
	@Operation(
	    summary = "Excluir tarefa",
	    description = "Remove uma tarefa do sistema"
	)
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id) {

		try {
			
			//Excluindo no banco de dados
			var tarefaRepository = new TarefaRepository();
			
			if(tarefaRepository.delete(id)) {
				return ResponseEntity.status(200).body("Tarefa excluída com sucesso.");
			}
			else {
				return ResponseEntity.status(404).body("Tarefa não encontrada. Verifique o ID informado.");
			}
		}
		catch(Exception e) {
			//HTTP 500 (Internal Server Error)
			return ResponseEntity.internalServerError()
					.body(e.getMessage());
		}
	}
	
	@Operation(
	    summary = "Consultar tarefas",
	    description = "Retorna uma lista de tarefas cadastradas no sistema, com opções de filtro"
	)
	@GetMapping("{dataMin}/{dataMax}")
	public ResponseEntity<?> get(@PathVariable LocalDate dataMin, @PathVariable LocalDate dataMax) {
		
		try {
			
			//Consultando as tarefas no banco de dados
			var tarefaRepository = new TarefaRepository();
			var lista = tarefaRepository.findAll(dataMin, dataMax);
			
			//retornando as tarefas
			return ResponseEntity.status(200).body(lista);
		}
		catch(Exception e) {
			//HTTP 500 (Internal Server Error)
			return ResponseEntity.internalServerError()
					.body(e.getMessage());
		}
	}
	
	@Operation(
			summary = "Quantidade de tarefas por prioridade",
			description = "Retorna uma lista com a quantidade de tarefas cadastradas para cada prioridade."
	)
	@GetMapping("groupby-prioridade")
	public ResponseEntity<?> getTarefaPrioridade() {
			
		try {
				
			var tarefaRepository = new TarefaRepository();
			var lista = tarefaRepository.groupByTarefaPrioridade();
				
			return ResponseEntity.status(200).body(lista);
		}
		catch(Exception e) {			
			return ResponseEntity.internalServerError()
						.body(e.getMessage());
		}
	}
	
	@Operation(
			summary = "Quantidade de tarefas por categoria",
			description = "Retorna uma lista com a quantidade de tarefas cadastradas para cada categoria."
	)
	@GetMapping("groupby-categoria")
	public ResponseEntity<?> getTarefaCategoria() {
			
		try {
				
			var tarefaRepository = new TarefaRepository();
			var lista = tarefaRepository.groupByTarefaCategoria();
				
			return ResponseEntity.status(200).body(lista);
		}
		catch(Exception e) {			
			return ResponseEntity.internalServerError()
						.body(e.getMessage());
		}
	}
}








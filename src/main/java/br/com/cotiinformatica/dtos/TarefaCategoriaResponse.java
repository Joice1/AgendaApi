package br.com.cotiinformatica.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TarefaCategoriaResponse {

	private String nomeCategoria;
	private Integer qtdTarefas;
}

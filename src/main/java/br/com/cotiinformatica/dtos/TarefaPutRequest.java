package br.com.cotiinformatica.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TarefaPutRequest {

	private String nome;
	private String data;
	private String prioridade;
	private String categoriaId;
	private Boolean finalizado;
}

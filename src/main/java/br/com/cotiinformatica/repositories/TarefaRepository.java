package br.com.cotiinformatica.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.dtos.TarefaCategoriaResponse;
import br.com.cotiinformatica.dtos.TarefaPrioridadeResponse;
import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.entities.Tarefa;
import br.com.cotiinformatica.enums.Prioridade;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class TarefaRepository {

	// Método para inserir uma tarefa no banco de dados
	public void insert(Tarefa tarefa) throws Exception {

		var sql = """
					insert into tarefa(id, nome, data, prioridade, finalizado, categoria_id)
					values(?,?,?,?,?,?)
				""";

		var connection = ConnectionFactory.getConnection();

		var statement = connection.prepareStatement(sql);
		statement.setObject(1, tarefa.getId());
		statement.setString(2, tarefa.getNome());
		statement.setDate(3, java.sql.Date.valueOf(tarefa.getData()));
		statement.setString(4, tarefa.getPrioridade().toString());
		statement.setBoolean(5, tarefa.getFinalizado());
		statement.setObject(6, tarefa.getCategoria().getId());
		statement.execute();

		connection.close();
	}

	// Método para atualizar uma tarefa no banco de dados
	public boolean update(Tarefa tarefa) throws Exception {

		var sql = """
					update tarefa set
						nome = ?,
						data = ?,
						prioridade = ?,
						finalizado =?,
						categoria_id
					where
						id = ?
				""";

		var connection = ConnectionFactory.getConnection();

		var statement = connection.prepareStatement(sql);
		statement.setString(1, tarefa.getNome());
		statement.setDate(2, java.sql.Date.valueOf(tarefa.getData()));
		statement.setString(3, tarefa.getPrioridade().toString());
		statement.setBoolean(4, tarefa.getFinalizado());
		statement.setObject(5, tarefa.getCategoria().getId());
		statement.setObject(6, tarefa.getId());
		var rowsAffected = statement.executeUpdate();

		connection.close();

		return rowsAffected > 0;
	}

	// Método para excluir uma tarefa no banco de dados
	public boolean delete(UUID id) throws Exception {

		var sql = """
					delete from tarefa
					where
						id = ?
				""";

		var connection = ConnectionFactory.getConnection();

		var statement = connection.prepareStatement(sql);
		statement.setObject(1, id);
		var rowsAffected = statement.executeUpdate();

		connection.close();

		return rowsAffected > 0;
	}

	// Método para consultar tarefas
	public List<Tarefa> findAll(LocalDate dataMin, LocalDate dataMax) throws Exception {

		var sql = """
						select 
							t.id as idtarefa, 
							t.nome as nometarefa, 
							t.data, 
							t.prioridade, 
							t.finalizado, 
							c.id as idcategoria, 
							c.nome as nomecategoria
						from tarefa t
						inner join categoria c on t.categoria_id = c.id
						where data between ? and ?
						order by data
				""";
		
		var connection = ConnectionFactory.getConnection();
		
		var statement = connection.prepareStatement(sql);
		statement.setDate(1, java.sql.Date.valueOf(dataMin));
		statement.setDate(2, java.sql.Date.valueOf(dataMax));
		var result = statement.executeQuery();
		
		var lista = new ArrayList<Tarefa>();
		
		while(result.next()) {
			
			var tarefa = new Tarefa();
			tarefa.setCategoria(new Categoria());
			
			tarefa.setId(UUID.fromString(result.getString("idtarefa")));
			tarefa.setNome(result.getString("nometarefa"));
			tarefa.setData(LocalDate.parse(result.getString("data")));
			tarefa.setPrioridade(Prioridade.valueOf(result.getString("prioridade")));
			tarefa.setFinalizado(result.getBoolean("finalizado"));
			tarefa.getCategoria().setId(UUID.fromString(result.getString("idcategoria")));
			tarefa.getCategoria().setNome(result.getString("nomecategoria"));
			
			lista.add(tarefa);
		}
		
		connection.close();
		
		return lista;
	}
	
	//Método para retornar a quantidade de tarefas por prioridade
	public List<TarefaPrioridadeResponse> groupByTarefaPrioridade() throws Exception {
		
		var sql = """
					select 
						t.prioridade,
						count(*) as qtdtarefas
					from tarefa t
					group by t.prioridade;
				""";
		
		var connection = ConnectionFactory.getConnection();
		
		var statement = connection.prepareStatement(sql);
		var result = statement.executeQuery();
		
		var lista = new ArrayList<TarefaPrioridadeResponse>();
		
		while(result.next()) {
			
			var tarefaPrioridade = new TarefaPrioridadeResponse();
			
			tarefaPrioridade.setPrioridade(result.getString("prioridade"));
			tarefaPrioridade.setQtdTarefas(result.getInt("qtdtarefas"));
			
			lista.add(tarefaPrioridade);
		}
		
		connection.close();
		return lista;
	}
	
	//Método para retornar a quantidade de tarefas por categoria
	public List<TarefaCategoriaResponse> groupByTarefaCategoria() throws Exception {
			
		var sql = """
					select
						c.nome as nomecategoria,
						count(*) as qtdtarefas
					from tarefa t
					inner join categoria c on t.categoria_id = c.id
					group by nomecategoria;
				""";
			
		var connection = ConnectionFactory.getConnection();
			
		var statement = connection.prepareStatement(sql);
		var result = statement.executeQuery();
			
		var lista = new ArrayList<TarefaCategoriaResponse>();
			
		while(result.next()) {
				
			var tarefaCategoria = new TarefaCategoriaResponse();
				
			tarefaCategoria.setNomeCategoria(result.getString("nomecategoria"));
			tarefaCategoria.setQtdTarefas(result.getInt("qtdtarefas"));
				
			lista.add(tarefaCategoria);
		}
			
		connection.close();
		return lista;
	}
}













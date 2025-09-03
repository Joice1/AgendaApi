--criação da tabela de categoria
create table categoria(
	id				uuid			primary key,
	nome			varchar(50)		not null unique
);

--cadastrando categorias
insert into categoria(id, nome) values(gen_random_uuid(), 'Trabalho');
insert into categoria(id, nome) values(gen_random_uuid(), 'Estudo');
insert into categoria(id, nome) values(gen_random_uuid(), 'Lazer');
insert into categoria(id, nome) values(gen_random_uuid(), 'Saúde');
insert into categoria(id, nome) values(gen_random_uuid(), 'Casa');
insert into categoria(id, nome) values(gen_random_uuid(), 'Família');
insert into categoria(id, nome) values(gen_random_uuid(), 'Outros');

--criação da tabela de tarefa
create table tarefa(
	id				uuid			primary key,
	nome			varchar(100)	not null,
	data			date			not null,
	prioridade		varchar(10)		not null,
	finalizado		boolean			not null,
	categoria_id	uuid			not null,
	foreign key(categoria_id)
		references categoria(id)
);

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
where data between '2025-08-01' and '2025-08-31'
order by data

select 
	t.prioridade,
	count(*) as qtdtarefas
from tarefa t
group by t.prioridade;

select
	c.nome as nomecategoria,
	count(*) as qtdtarefas
from tarefa t
inner join categoria c on t.categoria_id = c.id
group by nomecategoria;
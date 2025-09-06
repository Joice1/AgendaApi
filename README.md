# 🗓️ Agenda Web – Gerenciadora de Tarefas, Eventos e Compromissos

Sistema de **Agenda Web** desenvolvido com foco em **boas práticas de arquitetura**, organização de código e uso de tecnologias modernas no backend e banco de dados.

## 🚀 Tecnologias Utilizadas

- **Java 21 (LTS)** — lançado em **19 de setembro de 2023**, atualmente a versão LTS mais recente
- **Spring Boot**
- **Arquitetura Limpa (Clean Architecture)**
- **API RESTful**
- **Swagger** (documentação interativa da API)
- **Docker** (com `docker-compose`, para facilitar execução e deploy)
- **Banco de Dados**: PostgreSQL (via Docker)

## 📚 Endpoints da API – `/api/v1`

### Categorias
- `GET /api/v1/categorias` — Consulta de categorias

### Tarefas
- `GET /api/v1/tarefas` — Consulta de tarefas
- `POST /api/v1/tarefas` — Cadastro de tarefas
- `PUT /api/v1/tarefas` — Atualização de tarefas
- `DELETE /api/v1/tarefas` — Exclusão de tarefas

## 📄 Documentação da API

A documentação interativa da API está disponível via Swagger:

http://localhost:8080/swagger-ui.html

bash
Copiar código

## 🛠️ Como Rodar o Projeto

### Com Docker

1. Clone este repositório:
   ```bash
   git clone https://github.com/Joice1/AgendaApi.git
   cd AgendaApi
Execute o Docker Compose:

bash
Copiar código
docker-compose up --build
Isso iniciará:

Aplicação Spring Boot (porta 8082)

Banco de dados PostgreSQL (porta 5432)

Acesse:

API: http://localhost:8082/api/v1/tarefas

Swagger: http://localhost:8082/swagger-ui.html

✅ Boas Práticas Aplicadas
Clean Architecture (domínio, aplicação e infraestrutura bem separados)

Padrão REST

Injeção de dependência com Spring

Uso de DTOs, mapeadores e validações

Modularidade, testabilidade e escalabilidade

Swagger para documentação automática da API

🔜 Melhorias Futuras
 Autenticação e autorização 

 Notificações por e-mail

 Front‑end ( Angular)

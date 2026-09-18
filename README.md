# API de Votação Cooperativa

Esta é uma API REST desenvolvida em Java com Spring Boot para gerenciamento de assembleias e sessões de votação em cooperativas.

## 🚀 Tecnologias Utilizadas
- **Java 17+** (A aplicação usa Java recente)
- **Spring Boot 3** (Web, Data JPA, Validation)
- **PostgreSQL** (Banco de dados relacional)
- **Docker & Docker Compose** (Para subir o banco de dados)
- **Arquitetura Hexagonal** (Ports and Adapters)
- **Testes**: JUnit 5, Mockito, RestAssured, WireMock

## 🛠️ Como rodar o projeto localmente

### 1. Pré-requisitos
Certifique-se de ter instalado em sua máquina:
- **Docker** e **Docker Compose**

### 2. Subindo a Aplicação e o Banco de Dados (Tudo via Docker!)
Acesse a pasta raiz do projeto (`cooperative-voting-api`) e execute o comando abaixo. Ele construirá a imagem da API Java (multi-stage build) e subirá junto com o PostgreSQL:
```bash
docker-compose up -d --build
```
Isso criará dois contêineres:
1. `cpf-verification-db`: O PostgreSQL rodando.
2. `cpf-verification-api`: A API Spring Boot exposta na porta `8080`.

Aguarde alguns segundos até a API iniciar completamente. A API ficará disponível na porta: `http://localhost:8080`

### 3. Acessando a Documentação (Swagger)
Abra no seu navegador: `http://localhost:8080/swagger-ui.html`

## 🧪 Como testar os Endpoints

Abaixo estão exemplos de requisições utilizando o `cURL` (você também pode importar essas requisições no **Postman** ou **Insomnia**).

### 1. Criar uma Nova Pauta
```bash
curl -X POST http://localhost:8080/api/v1/pautas \
-H "Content-Type: application/json" \
-d '{"nome": "Aprovação do Orçamento 2024", "descricao": "Votação para definir o orçamento anual."}'
```
*Guarde o `id` retornado na resposta para usar nos próximos passos.*

### 2. Abrir uma Sessão de Votação
Substitua `<ID_DA_PAUTA>` pelo ID retornado na etapa anterior. O tempo padrão é 1 minuto (se omitir a duração).
```bash
curl -X POST http://localhost:8080/api/v1/sessoes \
-H "Content-Type: application/json" \
-d '{"pautaId": "<ID_DA_PAUTA>", "duracaoMinutos": 2}'
```
*Guarde o `id` (Id da Sessão) retornado na resposta para usar nos próximos passos.*

### 3. Registrar um Voto
Substitua `<ID_DA_SESSAO>` pelo ID da sessão retornado na etapa anterior. O CPF enviado será validado na API Externa.
```bash
curl -X POST http://localhost:8080/api/v1/sessoes/<ID_DA_SESSAO>/votos \
-H "Content-Type: application/json" \
-d '{"cpfAssociado": "12345678901", "valor": "SIM"}'
```
*(Nota: Lembre-se de enviar o voto em menos de 2 minutos, senão a sessão expira!)*

### 4. Contabilizar os Resultados
```bash
curl -X GET http://localhost:8080/api/v1/sessoes/<ID_DA_SESSAO>/resultados
```

## ✅ Executando os Testes Automatizados (TDD & E2E)
A aplicação possui 100% de cobertura das regras de negócio através de testes unitários e testes de integração com RestAssured e WireMock. Para rodar a suíte completa de testes:
```bash
./mvnw test
```

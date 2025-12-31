# 🎯 Desafio — API de Gestão de Cupons

API REST para cadastro, consulta e deleção de cupons, desenvolvida em **Java 21 + Spring Boot 3**, seguindo boas práticas de **arquitetura em camadas com domínio rico**, testes automatizados e execução via **Docker**.

---

## 🏗️ Arquitetura

O projeto segue uma separação clara entre camadas:

domain/ → Entidades e regras de negócio (Domínio Rico)
application/ → Casos de uso (orquestram o domínio)
infrastructure/ → Persistência e integração
api/ → Controllers, DTOs, mappers e validações


Principais decisões:

- ✔️ Regras encapsuladas no **domínio**
- ✔️ `UseCases` expõem operações da aplicação
- ✔️ `DTOs + Mapper` evitam expor entidades
- ✔️ Validações de negócio → `BusinessException`
- ✔️ Testes unitários e de integração
- ✔️ Banco em memória **H2**
- ✔️ Documentação automática com **Swagger**
- ✔️ Execução via **Docker + Docker Compose**

---

## 🛠️ Tecnologias

- Java 21  
- Spring Boot 3  
- Spring Data JPA  
- H2 Database  
- MapStruct  
- JUnit + MockMvc  
- Jacoco (Coverage)  
- Swagger (springdoc-openapi)  
- Docker / Docker Compose  

---

## 🚀 Executando o projeto

### ▶️ Rodar localmente

```bash
mvn clean package
mvn spring-boot:run

A API ficará disponível em:
http://localhost:8080
```
---

## 🧪 Testes
### Rodar testes
```bash
mvn test
```

## Gerar relatório de cobertura
```bash
mvn test jacoco:report
```

### Relatório:
```bash
target/site/jacoco/index.html
```
✔️ O projeto mantém ≥ 80% de cobertura nas regras de negócio

## 🐳 Executando com Docker
Build + Run
```bash
docker compose up --build
```

### API disponível em:
```bash
http://localhost:8080
```

### H2 Console:
```bash
http://localhost:8080/h2-console
```

### JDBC URL:
```bash
jdbc:h2:mem:testdb
```

## 📚 Swagger / OpenAPI
```bash
http://localhost:8080/swagger-ui.html
```

## 🧩 Endpoints

### ➕ Criar cupom

```bash
POST /coupon

{
  "code": "ABC-123",
  "description": "Cupom exemplo",
  "discountValue": 1.5,
  "expirationDate": "2030-01-01T10:00:00Z",
  "published": true
}
```
### 🔍 Buscar cupom por ID

```bash
GET /coupon/{id}
```

### 🗑️ Deletar cupom

```bash
DELETE /coupon/{id}
```

Retorno 204 No Content em sucesso

## 🧠 Regras de Negócio Implementadas

✔️ Código deve conter 6 caracteres alfanuméricos

✔️ Remove caracteres especiais automaticamente

✔️ Descrição é obrigatória

✔️ Valor mínimo do desconto: 0.5

✔️ Data de expiração não pode ser no passado

✔️ Cupom criado com status ACTIVE

✔️ Delete altera status para DELETED

✔️ Impede segunda exclusão

✔️ Impede criação com código duplicado

✔️ Todas as regras encapsuladas no Domínio

### Erros de domínio lançam:

BusinessException

## 📂 Estrutura do Projeto
```bash
src/main/java
 ├─ api/
 │   ├─ controller
 │   ├─ dto
 │   ├─ mapper
 │   └─ handler
 ├─ application/
 |   ├─ mapper 
 |   ├─ model
 │   └─ usecase
 ├─ config/
 ├─ domain/
 │   ├─ coupon
 │   ├─ enun
 │   └─ exception
 └─ infrastructure/
     ├─ entity
     ├─ mapper
     └─ repository
```

## 🧪 Testes Implementados
### ✔️ Unit Tests (Domínio)

validação de código

sanitização do código

desconto mínimo

data de expiração

criação válida

deleção e status

impede segunda exclusão

### ✔️ Integration Tests (API)

criação com sucesso

consulta por ID

exclusão

impede nova exclusão

cupom não encontrado

impede duplicidade de código

## 📌 Melhorias Futuras (opcional)

Paginação de cupons

Atualização parcial (PATCH)

Endpoint para resgate do cupom

Observabilidade / Metrics

## 👤 Autor

André Cristovam

Projeto desenvolvido para o desafio técnico — foco em qualidade de código, arquitetura limpa e testes.

## 🏁 Conclusão

Requisitos atendidos:

✔️ Regras no domínio

✔️ Banco H2

✔️ Testes ≥ 80%

✔️ Docker + Compose

✔️ Swagger

✔️ Código limpo e organizado
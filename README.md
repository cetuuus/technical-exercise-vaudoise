# Client and Contract Management Backend

This project is a **Java** and **Spring Boot** backend for managing clients (individuals and companies) and their contracts. It exposes a **RESTful API** that allows you to:
- Create, read, update, and delete clients.
- Manage contracts (creation, updates, and calculation of active contract costs).
- Provide a high-performance endpoint to calculate the sum of active contract costs per client.

---

## Setup

### Prerequisites
- **Docker** (to run the PostgreSQL container)
- **Java 17+**
- **Maven 3.8+**

### Running PostgreSQL with Docker
To start a PostgreSQL container, use the following command:
```bash
docker run -e POSTGRES_PASSWORD=<your_password> -v postgres_data:/var/lib/postgresql/data -p 5432:5432 -d postgres
```
Replace `<your_password>` with your chosen password.

---

# Technical Exercise Vaudoise

This project provides a REST API to manage Clients and Contracts.

## Running
Configure your database connection in `.env` (see `src/main/resources/application.properties` for env var names), then run:

- Maven: `./mvnw spring-boot:run`
- Or from IDE: run `TechnicalExerciseVaudoiseApplication`

## API Documentation (Swagger / OpenAPI)
Automatic API documentation is enabled via springdoc-openapi.

- Swagger UI: http://localhost:8080/swagger-ui (or /swagger-ui/index.html)
- OpenAPI JSON: http://localhost:8080/v3/api-docs

These endpoints are generated automatically from the controllers and DTO validations.

## Main Endpoints
- POST /clients
- GET /clients/{id}
- PUT /clients/{id}
- DELETE /clients/{id}
- POST /clients/{clientId}/contracts
- PATCH /contracts/{id}/amount
- GET /clients/{clientId}/contracts?updatedFrom=...&updatedTo=...
- GET /clients/{clientId}/contracts/active/sum

Dates use ISO 8601 format. Validation is applied to dates, phone numbers, emails and amounts.

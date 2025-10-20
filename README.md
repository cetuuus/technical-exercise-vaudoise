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

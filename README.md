# UniPDS — Quarkus Backend

> **Supersonic. Subatomic. Production-ready.**  
> A modern Java REST API built with [Quarkus 3](https://quarkus.io/), showcasing enterprise-grade features such as JWT security, fault tolerance, distributed tracing, health checks, and native compilation.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Security (JWT)](#security-jwt)
- [Health & Observability](#health--observability)
- [Distributed Tracing (OpenTelemetry)](#distributed-tracing-opentelemetry)
- [Packaging & Deployment](#packaging--deployment)
- [Native Compilation](#native-compilation)
- [Adding Extensions](#adding-extensions)

---

## Overview

**UniPDS** is a Quarkus-based REST API developed as a reference project for the UniPDS course. It demonstrates real-world backend engineering practices including:

- RESTful CRUD operations with JPA/Panache
- JWT-based authentication and role-based authorization
- MicroProfile Fault Tolerance (Circuit Breaker, Timeout, Fallback)
- SmallRye Health (Liveness & Readiness Checks)
- OpenTelemetry distributed tracing with Jaeger
- Integration with the Star Wars public API as an external REST client showcase
- OpenAPI/Swagger UI for interactive API documentation

---

## Architecture

```
unipds-quarkus/
└── src/main/java/com/artantech/
    ├── client/             # MicroProfile REST Client (e.g., StarWarsServices)
    ├── health/             # Liveness and Readiness health checks
    ├── model/              # JPA entities (Pessoa)
    ├── server/             # JAX-RS REST resources (endpoints)
    └── util/               # Utilities and helpers
```

### High-level Flow

```
Client → JAX-RS Resource → PanacheEntity (JPA/H2) → Database
              ↓
        JWT Validation (SmallRye JWT)
              ↓
        Fault Tolerance (Circuit Breaker / Timeout)
              ↓
        OpenTelemetry → Jaeger
```

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| **Java** | 21 (LTS) | Runtime |
| **Quarkus** | 3.27.2 | Framework |
| **Hibernate ORM + Panache** | via Quarkus BOM | ORM / Data layer |
| **H2 Database** | via Quarkus BOM | Embedded database (dev/test) |
| **SmallRye JWT** | via Quarkus BOM | JWT authentication |
| **SmallRye Health** | via Quarkus BOM | Health checks |
| **SmallRye Fault Tolerance** | via Quarkus BOM | Circuit breaker, timeout, fallback |
| **SmallRye OpenAPI** | via Quarkus BOM | API documentation (Swagger UI) |
| **Quarkus REST Client** | via Quarkus BOM | Declarative HTTP client |
| **OpenTelemetry + Jaeger** | via Quarkus BOM | Distributed tracing |
| **Maven** | 3.9+ | Build tool |

---

## Features

- ✅ **CRUD REST API** — Full Create/Read/Update/Delete for the `Pessoa` entity
- ✅ **JWT Security** — Role-based access control with `@RolesAllowed`
- ✅ **Fault Tolerance** — Circuit Breaker, Timeout, and Fallback patterns via MicroProfile
- ✅ **Health Checks** — Readiness probe integrates with the external Star Wars REST client
- ✅ **OpenTelemetry** — End-to-end distributed tracing exported to Jaeger
- ✅ **Swagger UI** — Interactive API docs at `/q/swagger-ui`
- ✅ **Quarkus Dev Mode** — Live reload with zero restarts
- ✅ **Native Compilation** — GraalVM native executable for ultra-fast startup

---

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21+** — [Download](https://adoptium.net/)
- **Apache Maven 3.9+** — [Download](https://maven.apache.org/)
- **GraalVM** *(optional, for native builds)* — [Download](https://www.graalvm.org/)
- **Docker or Podman** *(optional, for Jaeger tracing)* — [Docker](https://www.docker.com/) / [Podman](https://podman.io/)

---

## Getting Started

### Clone the repository

```bash
git clone https://github.com/artanniel/unipds-quarkus.git
cd unipds-quarkus
```

### Verify setup

```bash
java -version   # Requires Java 21+
mvn -version    # Requires Maven 3.9+
```

---

## Running the Application

### Development Mode (Live Reload)

The recommended way to run during development. Supports hot reload without restarting.

```bash
mvn clean quarkus:dev
```

> The Dev UI is available at: **http://localhost:8081/q/dev/**

### Production Mode (JAR)

```bash
# Build
mvn clean package

# Run
java -jar target/quarkus-app/quarkus-run.jar
```

---

## API Endpoints

All endpoints are documented interactively via **Swagger UI**:  
👉 **http://localhost:8081/q/swagger-ui**

### Pessoa Resource — `/api/pessoas`

| Method | Path | Description | Auth Required |
|--------|------|-------------|:---:|
| `GET` | `/pessoas` | List all people | ❌ |
| `GET` | `/pessoas/findByAnoNascimento?anoNascimento={year}` | Filter by birth year | ❌ |
| `POST` | `/pessoas` | Create a new person | ❌ |
| `PUT` | `/pessoas` | Update an existing person | ❌ |
| `DELETE` | `/pessoas` | Delete a person by ID | ❌ |

**Example Payload (Pessoa):**

```json
{
  "nome": "Artanniel Lima",
  "email": "artanniel@artantech.com",
  "anoNascimento": 1990
}
```

### Secure Resource — `/secure`

| Method | Path | Description | Auth Required |
|--------|------|-------------|:---:|
| `GET` | `/secure/claim/{id}` | Get person + JWT claims | ✅ `Subscriber` role |

### Star Wars Resource — `/api/starwars`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/starwars/starships` | Proxy to SWAPI — list all starships |

---

## Security (JWT)

This application uses **MicroProfile JWT (SmallRye JWT)** for authentication.

### Obtaining a Token

Public and private keys are available at the UniPDS reference repository:

```bash
# Fetch the pre-generated JWT token
token=$(curl https://raw.githubusercontent.com/eldermoraes/unipds/main/jwt-token/quarkus.jwt.token -s)

# Verify the token was fetched
echo $token
```

### Using the Token

Pass the token as a Bearer in the `Authorization` header:

```bash
curl -X GET http://localhost:8081/secure/claim/1 \
  -H "Authorization: Bearer $token" \
  -H "accept: application/json"
```

> **Key URLs:**  
> 🔑 Public Key: https://raw.githubusercontent.com/eldermoraes/unipds/main/jwt-token/quarkus.jwt.pub  
> 🔐 Private Key / Token: https://raw.githubusercontent.com/eldermoraes/unipds/main/jwt-token/quarkus.jwt.token

---

## Health & Observability

This application exposes **MicroProfile Health** endpoints for orchestration platforms (Kubernetes, OpenShift, etc.).

| Endpoint | Description |
|----------|-------------|
| `GET /q/health` | Combined health (liveness + readiness) |
| `GET /q/health/live` | Liveness probe |
| `GET /q/health/ready` | Readiness probe (checks Star Wars API connectivity) |

**Dev UI Health Dashboard:**  
http://localhost:8081/q/dev-ui/quarkus-smallrye-health/health

**Health endpoint:**  
http://localhost:8081/q/health/

![Health Check UI](src/main/resources/images/Screenshot%20from%202026-03-25%2022-11-48.png)

### Stress test the fallback behavior

```bash
while true; do
  curl -X GET 'http://localhost:8081/api/starwars/starships' \
    -H 'accept: application/json'
  sleep .3
done
```

---

## Distributed Tracing (OpenTelemetry)

The application is instrumented with **OpenTelemetry** and exports traces to **Jaeger**.

### Start Jaeger (Docker)

```bash
docker run --name=jaeger \
  -d \
  -p 16686:16686 \
  -p 4317:4317 \
  -e COLLECTOR_OTLP_ENABLED=true \
  jaegertracing/all-in-one:latest
```

### Start Jaeger (Podman)

```bash
podman run --name=jaeger \
  -d \
  -p 16686:16686 \
  -p 4317:4317 \
  -e COLLECTOR_OTLP_ENABLED=true \
  jaegertracing/all-in-one:latest
```

### Access Jaeger UI

👉 **http://localhost:16686**

---

## Packaging & Deployment

### Standard JAR

```bash
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar
```

> **Note:** This is not an über-jar. Dependencies are in `target/quarkus-app/lib/`.

### Über-JAR (self-contained)

```bash
mvn clean package -Dquarkus.package.jar.type=uber-jar
java -jar target/*-runner.jar
```

---

## Native Compilation

Compile the application to a native binary for **near-instant startup** and **minimal memory footprint**.

### With GraalVM installed locally

```bash
mvn clean package -Dnative
./target/unipds-1.0.0-SNAPSHOT-runner
```

### Without GraalVM (using container build)

```bash
mvn clean package -Dnative -Dquarkus.native.container-build=true
./target/unipds-1.0.0-SNAPSHOT-runner
```

### Verify build artifacts

```bash
ls -lh target/
```

> 📖 For more details, see the [Quarkus Maven Tooling Guide](https://quarkus.io/guides/maven-tooling).

---

## Adding Extensions

Browse and add Quarkus extensions via the [Quarkus Extension Registry](https://code.quarkus.io/).

```bash
# Example: add the REST Client extension
./mvnw quarkus:add-extension -Dextensions="io.quarkus:quarkus-rest-client"
```

---

## Related Guides

- 📘 [Quarkus REST (JAX-RS)](https://quarkus.io/guides/rest) — Build time optimized REST endpoints
- 📘 [SmallRye OpenAPI](https://quarkus.io/guides/openapi-swaggerui) — Swagger UI + OpenAPI spec generation
- 📘 [MicroProfile JWT](https://quarkus.io/guides/security-jwt) — JWT-based authentication
- 📘 [SmallRye Fault Tolerance](https://quarkus.io/guides/smallrye-fault-tolerance) — Circuit breaker & fallback
- 📘 [SmallRye Health](https://quarkus.io/guides/smallrye-health) — Health check probes
- 📘 [Hibernate ORM + Panache](https://quarkus.io/guides/hibernate-orm-panache) — Simplified JPA
- 📘 [OpenTelemetry](https://quarkus.io/guides/opentelemetry) — Distributed tracing

---

<div align="center">


**Built with ☕ Java 21 · ⚡ Quarkus 3 · 🛡️ MicroProfile by artantech**

</div>
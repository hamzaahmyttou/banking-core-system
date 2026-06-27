# Banking Core System

A modern banking backend application built with Java 21 and Spring Boot 3, designed to simulate the core functionalities of a banking information system.

This project follows clean architecture principles and demonstrates enterprise backend development practices, including layered architecture, validation, exception handling, DTO mapping, and automated testing.

---

## Features

### Customer Management

* Create customer
* Retrieve customer by ID
* Retrieve all customers
* Update customer
* Delete customer
* Input validation
* Global exception handling

### Account Management

* Open bank account
* Generate unique IBAN
* Retrieve account by ID
* Retrieve all accounts
* Retrieve all accounts of a customer
* Check account balance
* Close account

---

## Technology Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Validation

### Database

* PostgreSQL

### API Documentation

* OpenAPI / Swagger

### Testing

* JUnit 5
* Mockito
* Spring Boot Test
* MockMvc

### Build

* Maven

### DevOps

* Docker

---

## Project structure

```text
src
├── account
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── mapper
│   ├── repository
│   └── service
│
├── customer
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── mapper
│   ├── repository
│   └── service
│
├── common
│   ├── dto
│   └── util
│
└── exception
```

---

## REST API

### Customer API

| Method | Endpoint               |
| ------ | ---------------------- |
| POST   | /api/v1/customers      |
| GET    | /api/v1/customers      |
| GET    | /api/v1/customers/{id} |
| PUT    | /api/v1/customers/{id} |
| DELETE | /api/v1/customers/{id} |

### Account API

| Method | Endpoint                               |
| ------ | -------------------------------------- |
| POST   | /api/v1/accounts                       |
| GET    | /api/v1/accounts                       |
| GET    | /api/v1/accounts/{id}                  |
| GET    | /api/v1/accounts/customer/{customerId} |
| GET    | /api/v1/accounts/{id}/balance          |
| PATCH  | /api/v1/accounts/{id}/close            |

---

## Testing

### Unit Tests

* CustomerService
* AccountService

### Controller Tests

* CustomerController
* AccountController

### Tested Scenarios

* Customer CRUD
* Account CRUD
* Validation
* Exception handling
* IBAN generation
* REST endpoints

---

## Roadmap

### Completed

* Customer module
* Account module
* DTO mapping
* Bean Validation
* Global exception handling
* Unit tests
* Controller tests

### In Progress

* Transaction module

### Planned

* Deposit
* Withdrawal
* Money transfer
* Transaction history
* JWT Authentication
* Role-based authorization
* Docker & Docker Compose
* CI/CD with GitHub Actions
* Kafka notifications
* Redis caching
* Microservices architecture

---

## Design Principles

* Layered Architecture
* SOLID Principles
* Separation of Concerns
* DTO Pattern
* Repository Pattern
* Dependency Injection
* Exception-Driven Design

---

## Author

Software Engineer specialized in Payment Systems (Monetics)

Main interests:

* Banking Information Systems
* Payment Systems
* Java Backend Development
* Spring Ecosystem
* Software Architecture

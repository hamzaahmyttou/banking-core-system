# Banking Core System

A modern banking backend application built with Java 21 and Spring Boot 3, designed to simulate the core functionalities of a banking information system.

This project follows clean architecture principles and demonstrates banking business rules, transaction management, concurrency handling, testing, and REST API best practices.

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

### Transaction Management

* Deposit
* Withdrawal
* Transfer between accounts
* Account transaction history
* Get transaction by ID

---

## Banking Business Rules

* Pessimistic locking (PESSIMISTIC_WRITE)
* Concurrent transfer protection
* Deadlock prevention
* Balance validation
* Active account validation
* Atomic transactions using @Transactional
* Global exception handling
* DTO pattern
* Mapper pattern

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
├── transaction
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

## Run locally

```bash
docker-compose up --build
```

---

## API Endpoints

### Customers

| Method | Endpoint               |
| ------ | ---------------------- |
| POST   | /api/v1/customers      |
| GET    | /api/v1/customers      |
| GET    | /api/v1/customers/{id} |
| PUT    | /api/v1/customers/{id} |
| DELETE | /api/v1/customers/{id} |

### Accounts

| Method | Endpoint                       |
| ------ | ------------------------------ |
| POST   | /api/v1/accounts               |
| GET    | /api/v1/accounts/{id}          |
| GET    | /api/v1/accounts/{id}/balance  |
| PATCH  | /api/v1/accounts/{id}/activate |
| PATCH  | /api/v1/accounts/{id}/close    |

### Transactions

| Method | Endpoint                                 |
| ------ | ---------------------------------------- |
| POST   | /api/v1/transactions/deposit             |
| POST   | /api/v1/transactions/withdraw            |
| POST   | /api/v1/transactions/transfer            |
| GET    | /api/v1/transactions/{id}                |
| GET    | /api/v1/transactions/account/{accountId} |

---

## Testing

### Unit Tests

* AccountService
* CustomerService
* TransactionService

### Controller Tests

* AccountController
* CustomerController
* TransactionController

More than **50 unit tests** covering business logic and REST controllers.

---

## Author

Software Engineer specialized in Payment Systems (Monetics)

Main interests:

* Banking Information Systems
* Payment Systems
* Java Backend Development
* Spring Ecosystem
* Software Architecture

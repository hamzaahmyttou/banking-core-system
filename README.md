# Banking Core System

A banking backend application developed with Java Spring Boot to simulate the core functionalities of a banking information system.

## Overview

This project aims to reproduce the main components of a banking core system:

* Customer management
* Account management
* Deposits
* Withdrawals
* Transfers
* Transaction history
* Audit and exception handling

The objective is to demonstrate backend development skills, software architecture principles, and banking domain knowledge.

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Validation

### Database

* PostgreSQL

### Documentation

* OpenAPI / Swagger

### Testing

* JUnit 5
* Mockito
* Spring Boot Test

### DevOps

* Docker
* Docker Compose

---

## Architecture

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Project structure:

```text
customer
 ├── controller
 ├── service
 ├── repository
 ├── entity
 └── dto

account
 ├── controller
 ├── service
 ├── repository
 ├── entity
 └── dto

transaction
 ├── controller
 ├── service
 ├── repository
 ├── entity
 └── dto
```

---

## Current Features

### Customer Management

* Create customer
* Retrieve customer by ID
* Retrieve all customers
* Update customer
* Delete customer

### Validation

* CIN validation
* Email validation
* Required fields validation

### Exception Handling

* Customer not found
* Customer already exists
* Validation errors
* Global exception management

---

## API Endpoints

### Customers

| Method | Endpoint               | Description        |
| ------ | ---------------------- | ------------------ |
| POST   | /api/v1/customers      | Create customer    |
| GET    | /api/v1/customers      | Get all customers  |
| GET    | /api/v1/customers/{id} | Get customer by id |
| PUT    | /api/v1/customers/{id} | Update customer    |
| DELETE | /api/v1/customers/{id} | Delete customer    |

---

## Testing

### Service Tests

* Customer creation
* Customer retrieval
* Customer deletion
* Business exceptions

### Controller Tests

* POST endpoint
* GET endpoint
* PUT endpoint
* DELETE endpoint
* Validation tests

---

## Roadmap

### Phase 1

* Customer module

### Phase 2

* Account module
* Transaction module

### Phase 3

* JWT Authentication
* Role management
* Audit logging
* Docker Compose

### Phase 4

* Kafka integration
* Notification service
* Redis cache
* Microservices architecture

---

## Author

Software Engineer specialized in Payment Systems (Monetics).

Focus areas:

* Banking Systems
* Payment Systems
* Backend Development
* Java & Spring Ecosystem

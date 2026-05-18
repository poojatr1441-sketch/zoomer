# Zoomer Backend

A scalable food delivery backend system inspired by Zomato and Swiggy, built using Spring Boot.

This project supports:
- Customer ordering flow
- Restaurant owner management
- Delivery agent workflow
- JWT authentication & authorization
- Cart and addon system
- Order tracking
- Snapshot-based billing
- Docker support
- Swagger API documentation

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- PostgreSQL
- Spring Data JPA
- Maven
- JUnit & Mockito
- Docker
- Swagger/OpenAPI

---## Features

### Customer Module
- User registration and login
- JWT-based authentication
- Address management
- Cart management with addons
- Place order flow
- Track order status
- Cancel order functionality

### Restaurant Owner Module
- Add and manage menu items
- Accept or reject customer orders
- Manage restaurant orders

### Delivery Agent Module
- Pickup assigned orders
- Mark orders as delivered

### Admin Module
- Role-based access support
- User and order management foundation

---

## Security Features

- JWT Authentication
- Role-based authorization
- Protected endpoints
- Owner authorization validation
- Cross-owner access prevention

---

## Testing

Implemented unit testing using:
- JUnit 5
- Mockito
- MockMvc

### Tested Scenarios
- Empty cart validation
- Price snapshot integrity verification
- Unauthorized owner access validation
- Order workflow validation

---

## Architecture

Layered Architecture:

Controller → Service → Repository → Database

This architecture improves:
- Maintainability
- Scalability
- Testability
- Separation of concerns

---## Swagger API Documentation

Swagger UI is enabled for API testing and endpoint documentation.

Access Swagger UI:
```bash
http://localhost:8080/swagger-ui/index.html
```

---

## Docker Support

This project supports Docker containerization.

### Build Docker Image

```bash
docker build -t zoomer-app .
```

### Run Docker Container

```bash
docker run -p 8080:8080 zoomer-app
```

---

## How to Run Locally

### Clone Repository

```bash
git clone <your-github-repo-link>
```

### Navigate to Project

```bash
cd ZoomerApp
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Database Configuration

Update the following properties in:

```bash
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/zoomer
spring.datasource.username=postgres
spring.datasource.password=your_password
```

---

## Future Improvements

- Payment gateway integration
- Redis caching
- Kafka event-driven architecture
- Microservices migration
- CI/CD pipeline
- Kubernetes deployment

---## Author

Pooja T R

Backend Developer focused on Java, Spring Boot, PostgreSQL, Security, and scalable backend systems.

---
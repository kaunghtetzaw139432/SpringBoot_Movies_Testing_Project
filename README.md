# Movie Management Project (Spring Boot)

This is a **Spring Boot CRUD application** for managing movies.  
It demonstrates **Spring Data JPA**, **REST APIs**, **H2 in-memory database**, and **integration & repository testing** with JUnit and RestTemplate.

---

## Features

- Create, Read, Update, Delete (CRUD) movies
- RESTful endpoints:
  - `POST /movies` → create a movie
  - `GET /movies` → list all movies
  - `GET /movies/{id}` → get a movie by ID
  - `PUT /movies/{id}` → update a movie
  - `DELETE /movies/{id}` → delete a movie
- Filter movies by genre
- Integration tests using **RestTemplate**
- Repository tests using **Spring Data JPA**
- H2 in-memory database for testing

---

## Technology Stack

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- H2 Database (in-memory for testing)
- MySQL (optional for production)
- JUnit 5 + Mockito for unit & integration tests
- Maven for build management
- Lombok for boilerplate code reduction

---

## Prerequisites

- JDK 17+
- Maven 3+
- MySQL (optional)
- IDE (IntelliJ, Eclipse, VS Code)

---

## Setup and Running the Project

1. Clone the repository:

```bash

REST API Examples
Create a Movie
POST /movies
Content-Type: application/json

{
    "name": "Avatar",
    "genera": "Action",
    "releasedDate": "2000-04-22"
}

Get Movie by ID
GET /movies/1

Update Movie
PUT /movies/1
Content-Type: application/json

{
    "name": "Avatar",
    "genera": "Fantacy",
    "releasedDate": "2000-04-22"
}

Delete Movie
DELETE /movies/1

Testing

Repository Tests → @DataJpaTest

CRUD operations verified on H2 in-memory DB

Integration Tests → @SpringBootTest + RestTemplate

Create, Read, Update, Delete movies via REST API

Run all tests:

mvn test

MySQL Configuration (Optional)

To use MySQL instead of H2:

spring.datasource.url=jdbc:mysql://localhost:3306/movies_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect




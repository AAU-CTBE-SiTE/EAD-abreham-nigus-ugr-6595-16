# Lab 8: AI-Assisted RESTful CRUD API

A RESTful CRUD API for students, built with Spring Boot, Spring Data JPA and MySQL.

## AI chat history

The full AI conversation for this lab:
https://claude.ai/share/1263e4ae-d2b3-49b1-adcf-c65eccd996af

## Technologies

- Java 21, Maven
- Spring Boot 4.1.2 (Spring Web, Spring Data JPA)
- MySQL 8.4
- Lombok

## Project structure

```
src/main/java/com/craft/studentapi/
├── StudentApiApplication.java      main class
├── model/Student.java              JPA entity (id, firstName, lastName, email, department)
├── repository/StudentRepository.java   Spring Data JPA repository
├── service/StudentService.java     CRUD logic
└── controller/StudentController.java   REST endpoints
src/main/resources/application.properties   MySQL and JPA settings
```

## How to run

1. Start MySQL on `localhost:3306`. The database `student_db` is created automatically.
2. Set your MySQL username and password in `src/main/resources/application.properties`.
3. Run `StudentApiApplication` in IntelliJ, or run `./mvnw spring-boot:run`.

The app runs on `http://localhost:8081`.

## Endpoints

| Method | URL | Action |
|---|---|---|
| POST | `/api/students` | Create a student |
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get one student (empty body if not found) |
| PUT | `/api/students/{id}` | Update a student (empty body if not found) |
| DELETE | `/api/students/{id}` | Delete a student |

## Example requests

```bash
curl -i -X POST http://localhost:8081/api/students \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Abebe","lastName":"Kebede","email":"abebe@aau.edu.et","department":"Software Engineering"}'

curl -i http://localhost:8081/api/students

curl -i http://localhost:8081/api/students/1

curl -i -X PUT http://localhost:8081/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Abebe","lastName":"Kebede","email":"abebe.k@aau.edu.et","department":"Information Systems"}'

curl -i -X DELETE http://localhost:8081/api/students/1
```

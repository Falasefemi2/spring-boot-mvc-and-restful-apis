# Spring Boot MVC and RESTful APIs

A comprehensive Spring Boot REST API project demonstrating complete MVC architecture, persistence layer, input validation, exception handling, and API response transformation for an Employee Management System.

## Table of Contents

- [Overview](#overview)
- [Key Concepts](#key-concepts)
- [Learning Resources](#learning-resources)
- [Key Features](#key-features)

## Overview

Employee Management REST API built with Spring Boot demonstrating:

- Three-layer MVC architecture (Controller → Service → Repository)
- RESTful endpoints with full CRUD operations
- JPA persistence with H2 database
- Bean validation with custom validators
- Global exception handling and response transformation
- DTO pattern with ModelMapper

## Key Concepts

### MVC Architecture

Three-layer separation with dependency injection:

- **Controller** - Handles HTTP requests, path variables, request bodies
- **Service** - Business logic and DTO-Entity mapping
- **Repository** - JPA data access extending `JpaRepository`

Supports GET, POST, PUT, PATCH, DELETE operations.

### Persistence Layer

Spring Data JPA repository with automatic CRUD methods:

```java
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> { }
```

Uses ModelMapper for Entity-DTO conversion. H2 database configured with file persistence and console access.

### Service Layer

Handles business logic and data transformation using ModelMapper:

```java
public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
    EmployeeEntity entity = modelMapper.map(inputEmployee, EmployeeEntity.class);
    return modelMapper.map(employeeRepository.save(entity), EmployeeDTO.class);
}
```

### Input Validation

Bean Validation annotations on DTO fields:

```java
@NotBlank(message = "Name cannot be blank")
@Size(min = 3, max = 10)
private String name;

@Email(message = "Email should be valid")
private String email;

@Min(18) @Max(80)
private Integer age;

@EmployeeRoleValidation  // Custom validator
private String role;
```

Custom validator checks role against allowed values (USER, ADMIN).

### Exception Handling

`@RestControllerAdvice` catches exceptions globally:

```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException e) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiResponse<?>> handleValidationErrors(MethodArgumentNotValidException e) {
    List<String> errors = e.getBindingResult().getAllErrors()
        .stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
    return buildErrorResponse(HttpStatus.BAD_REQUEST, "Input validation failed", errors);
}
```

### Response Transformation

`ResponseBodyAdvice` wraps all responses in standard format:

```java
public Object beforeBodyWrite(Object body, ...) {
    return body instanceof ApiResponse<?> ? body : new ApiResponse<>(body);
}
```

Success response:

```json
{
  "timestamp": "03:45:12 25-12-2025",
  "data": { "id": 1, "name": "John" },
  "error": null
}
```

Error response:

```json
{
  "timestamp": "03:45:12 25-12-2025",
  "data": null,
  "error": {
    "status": "BAD_REQUEST",
    "message": "Input validation failed",
    "subErrors": ["Email should be in valid format"]
  }
}
```

## Learning Resources

PDF notes are available in the `notes/` directory:

- [MVC Architecture](./notes/01_MVC_Architecture.pdf)
- [Presentation Layer](./notes/02_Presentation_Layer.pdf)
- [Persistence Layer](./notes/03_Persistence_Layer.pdf)
- [Service Layer](./notes/04_Service_Layer.pdf)
- [Controller Upgrades](./notes/05_Controller_Upgrades.pdf)
- [Input Validation](./notes/06_Input_Validation.pdf)
- [Exception Handling](./notes/07_Exception_Handling.pdf)
- [Transforming API Response](./notes/08_Transforming_API_Response.pdf)

## Key Features

- Complete MVC architecture with three-layer separation
- RESTful API with GET, POST, PUT, PATCH, DELETE
- JPA/Hibernate with H2 database
- Entity-DTO pattern using ModelMapper
- Bean Validation with custom validators
- Global exception handling
- Standardized API response format
- PATCH support using reflection for partial updates
- Constructor-based dependency injection

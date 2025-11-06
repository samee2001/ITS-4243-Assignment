# Student Management REST API

A comprehensive REST API for managing students built with **Spring Boot 3.5.7**, **Java 21**, and **PostgreSQL**. This project follows 2025 industry standards with a clean layered architecture, comprehensive validation, global exception handling, and Swagger documentation.

## 🚀 Features

### Core Features
- ✅ **CRUD Operations**: Create, Read, Update, and Delete students
- ✅ **Field Validation**: Email format, age constraints (≥18), not null constraints
- ✅ **Service Layer Architecture**: Proper separation of concerns (Controller → Service → Repository)
- ✅ **Global Exception Handling**: Centralized error handling with proper HTTP status codes
- ✅ **PostgreSQL Integration**: Persistent data storage with Spring Data JPA

### Bonus Features
- ✅ **Swagger UI**: Interactive API documentation and testing
- ✅ **Pagination & Sorting**: Efficient data retrieval with customizable pagination
- ✅ **Search Functionality**: Search students by name or course
- ✅ **Modern Architecture**: DTOs, proper exception handling, logging

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **PostgreSQL 12+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

## 🛠️ Setup Instructions

### 1. Database Setup

1. **Create PostgreSQL Database:**
   ```sql
   CREATE DATABASE student_management;
   ```

2. **Run the initialization script** (optional):
   ```bash
   psql -U postgres -d student_management -f database/init.sql
   ```

3. **Update Database Credentials** (if needed):
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/student_management
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   ```

### 2. Build and Run

#### Using Maven:
```bash
# Clean and build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

#### Using IDE:
1. Import the project as a Maven project
2. Run `Assignment01Application.java`

### 3. Verify Application

Once the application starts, you should see:
```
Started Assignment01Application in X.XXX seconds
```

The application will be available at:
- **Base URL**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

## 📚 API Endpoints

### Base URL
```
http://localhost:8080/api/students
```

### 1. Create Student
- **Method**: `POST`
- **Endpoint**: `/api/students`
- **Request Body**:
  ```json
  {
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "course": "Software Engineering",
    "age": 22
  }
  ```
- **Response**: `201 Created`
  ```json
  {
    "success": true,
    "message": "Student created successfully",
    "data": {
      "id": 2,
      "name": "Jane Smith",
      "email": "jane.smith@example.com",
      "course": "Software Engineering",
      "age": 22,
      "createdAt": "2025-01-15T10:35:00",
      "updatedAt": "2025-01-15T10:35:00"
    },
    "timestamp": "2025-01-15T10:35:00"
  }
  ```

### 2. Get All Students
- **Method**: `GET`
- **Endpoint**: `/api/students`
- **Query Parameters**:
  - `page` (default: 0): Page number (0-indexed)
  - `size` (default: 10): Page size
  - `sortBy` (default: "id"): Field to sort by (name, email, course, age, createdAt, updatedAt)
  - `sortDir` (default: "asc"): Sort direction (asc, desc)
  - `search` (optional): Search term for name or course
- **Example**: `/api/students?page=0&size=10&sortBy=name&sortDir=asc&search=john`
- **Response**: `200 OK`

### 3. Get Student By ID
- **Method**: `GET`
- **Endpoint**: `/api/students/{id}`
- **Example**: `/api/students/1`
- **Response**: `200 OK` or `404 Not Found`

### 4. Update Student
- **Method**: `PUT`
- **Endpoint**: `/api/students/{id}`
- **Request Body**: Same as Create Student
- **Response**: `200 OK`, `404 Not Found`, or `409 Conflict`

### 5. Delete Student
- **Method**: `DELETE`
- **Endpoint**: `/api/students/{id}`
- **Example**: `/api/students/1`
- **Response**: `200 OK` or `404 Not Found`

## 📊 HTTP Status Codes

- `200 OK`: Successful request
- `201 Created`: Resource created successfully
- `400 Bad Request`: Validation error or invalid input
- `404 Not Found`: Resource not found
- `409 Conflict`: Duplicate resource (e.g., email already exists)
- `500 Internal Server Error`: Unexpected server error

## 🧪 Testing with Swagger UI

1. **Access Swagger UI**: Navigate to `http://localhost:8080/swagger-ui.html`
2. **Explore API**: Browse all available endpoints
3. **Test Endpoints**: Use the "Try it out" feature to test each endpoint
4. **View Schemas**: Check request/response models

## 🧪 Testing with Postman

### Import Collection
1. Open Postman
2. Create a new collection: "Student Management API"
3. Add the following requests:

#### Create Student
```
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```

#### Get All Students
```
GET http://localhost:8080/api/students?page=0&size=10&sortBy=name&sortDir=asc
```

#### Get Student By ID
```
GET http://localhost:8080/api/students/1
```

#### Update Student
```
PUT http://localhost:8080/api/students/1
Content-Type: application/json

{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "course": "Mathematics",
  "age": 21
}
```

#### Delete Student
```
DELETE http://localhost:8080/api/students/1
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/assignment/Assignment/_1/
│   │       ├── Assignment01Application.java
│   │       ├── config/
│   │       │   └── SwaggerConfig.java
│   │       ├── controller/
│   │       │   └── StudentController.java
│   │       ├── dto/
│   │       │   ├── ApiResponse.java
│   │       │   ├── StudentRequestDto.java
│   │       │   └── StudentResponseDto.java
│   │       ├── entity/
│   │       │   └── Student.java
│   │       ├── exception/
│   │       │   ├── DuplicateResourceException.java
│   │       │   ├── GlobalExceptionHandler.java
│   │       │   └── ResourceNotFoundException.java
│   │       ├── repository/
│   │       │   └── StudentRepository.java
│   │       └── service/
│   │           ├── StudentService.java
│   │           └── impl/
│   │               └── StudentServiceImpl.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/assignment/Assignment/_1/
            └── Assignment01ApplicationTests.java
```

## 🏗️ Architecture

### Layered Architecture
- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Business logic and validation
- **Repository Layer**: Data access operations
- **Entity Layer**: JPA entities
- **DTO Layer**: Data transfer objects for request/response
- **Exception Layer**: Global exception handling

### Design Patterns
- **DTO Pattern**: Separation of entity and API contracts
- **Service Layer Pattern**: Business logic abstraction
- **Repository Pattern**: Data access abstraction
- **Global Exception Handling**: Centralized error management

## 🔧 Technologies Used

- **Spring Boot**: 3.5.7
- **Java**: 21
- **PostgreSQL**: Database
- **Spring Data JPA**: Data persistence
- **Spring Validation**: Field validation
- **Lombok**: Boilerplate code reduction
- **Swagger/OpenAPI**: API documentation
- **Maven**: Build tool

## 📝 Validation Rules

- **Name**: Required, 2-100 characters
- **Email**: Required, valid email format, unique
- **Course**: Required, 2-100 characters
- **Age**: Required, minimum 18, maximum 120

## 🐛 Troubleshooting

### Database Connection Issues
- Verify PostgreSQL is running
- Check database credentials in `application.properties`
- Ensure database `student_management` exists

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`

### Build Errors
- Clean Maven cache: `mvn clean`
- Update dependencies: `mvn dependency:resolve`

## 📄 License

This project is part of an academic assignment for ITS 4243 - Microservices and Cloud Computing.

## 👤 Author

Student Management API - Assignment 01

---

**Note**: Make sure PostgreSQL is running before starting the application. The application will automatically create the `students` table on first run if it doesn't exist.


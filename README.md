# Flight API (Spring Boot + MySQL)

REST API backing the Flight CLI.

## Requirements
- Java 17+
- Maven 3.9+
- MySQL 8 (with schema created)
- `application.properties` configured

## Run
```bash
# Start API
mvn -DskipTests spring-boot:run
```

Key Endpoints

GET /cities, GET /cities/{id}, GET /cities/{id}/airports

GET /aircraft/{id}/airports

GET /passengers

GET /passengers/{id}/aircraft

GET /passengers/{id}/airports

CRUD (admin) Endpoints

GET/POST /admin/passengers

GET/PUT/DELETE /admin/passengers/{id}

# helloworld.secured-rest-api.quarkus.demo
Demo of a quarkus application which exposes a secured REST API

## Prerequisites
* JDK 21
* Maven 3.9.8

## Features
* HTTPS
* Exposes an authenticate POST endpoint which authenticate user and provides a JWT token
* Exposes an anonymous non secured Greeting GET endpoint (no token has to be provided)
* Exposes a secured Greeting GET endpoint (token has to be provided)

## Technical choices

### Application
* Auto-executable Quarkus application
* REST API is implemented with reactive Quarkus Mutiny framework
* REST API documentation is provided at [Swagger UI]

### Unit tests
* JUnit5 and Mockito unit tests for execution eficiency
* Everything in the service to be tested has to be mocked

### End2End tests
* TODO

## Generate self-signed certificate
* TODO

## Dev mode
* `mvn quarkus:dev`

## Manual build
* `mvn clean package surefire-report:report jacoco:report`

### Unit tests HTML report and Jacoco code coverage HTML report
* HTML unit tests report is located at `target/reports/surefire.html`
* Code coverage report is located at `target/site/jacoco/index.html`

## Run

### JAR
* `java -jar target/quarkus-app/quarkus-run.jar -Dquarkus.config.locations=src/main/resources/`

### Docker
* TODO

### Debug
* TODO

## Test

### Swagger UI
* Swagger UI is available at : [Swagger UI]

[Swagger UI]: http://localhost:8080/q/swagger-ui/
[Swagger api-docs]: http://localhost:8080/q/openapi
[Quarkus health]: http://localhost:8080/q/health
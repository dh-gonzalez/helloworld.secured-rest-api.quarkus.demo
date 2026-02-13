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
* End2End tests implemented with [Karate]

## Generate self-signed certificate
* `keytool -genkeypair -alias helloworld-demo-keystore -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore helloworld-demo-keystore.p12 -validity 3650`
* `password` is the provided password for keystore generation

## Manual build
* `mvn clean package surefire-report:report jacoco:report`
* `docker build . --file src/main/docker/Dockerfile.jvm --tag ghcr.io/dh-gonzalez/helloworld.secured-rest-api.quarkus.demo:latest`

### Unit tests HTML report and Jacoco code coverage HTML report
* HTML unit tests report is located at `target/reports/surefire.html`
* Code coverage report is located at `target/site/jacoco/index.html`

## Run

### Dev mode
* `mvn quarkus:dev`

### JAR
* `java -jar "-Dquarkus.profile=dev" "-Dquarkus.config.locations=src/main/resources/" target/quarkus-app/quarkus-run.jar`

### Docker
* `docker run --name helloworld-container -d -p 8443:8443 -v {absolute/path/to/keystore}:/keystore ghcr.io/dh-gonzalez/helloworld.secured-rest-api.quarkus.demo:latest`

#### Debug
* `docker logs -f helloworld-container`
* `docker exec -it helloworld-container /bin/sh`

## Test

### Swagger UI
* Swagger UI is available at : [Swagger UI]

### Non secured endpoint
* `curl --silent --insecure -X GET https://localhost:8443/api/v1/anonymous/greeting` should answer `{"greeting":"Hello, World!"}`

### Secured endpoint
* Authenticate and receive token
    * `curl --silent --insecure -X POST https://localhost:8443/api/v1/authenticate -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}'` should answer `{"token":"the_token","type":"Bearer","expiresInSeconds":3600}`
* Request secured endpoint providing token
    * `curl --silent --insecure -H "Authorization: Bearer the_token" -X GET https://localhost:8443/api/v1/secured/greeting?name=David` should answer `{"greeting":"Hello, David!"}`

## End2End tests
* Start the application locally
* `mvn test -Pe2e`

### End2End tests HTML report
* located at `target\karate-reports\karate-summary.html`

### TODOs
* Enable files `Dockerfile.legacy-jar`, `Dockerfile.native` and `Dockerfile.native-micro` for building corresponding docker images
* Enable corresponding docker build in CI/CD

[Swagger UI]: https://localhost:8443/q/swagger-ui/
[Swagger api-docs]: https://localhost:8443/q/openapi
[Quarkus health]: https://localhost:8443/q/health
[Karate]: https://github.com/karatelabs/karate

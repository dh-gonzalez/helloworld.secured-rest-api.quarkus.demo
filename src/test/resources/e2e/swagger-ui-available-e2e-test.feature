Feature: Test swagger UI is available

Background:
* url 'https://localhost:8443'

Scenario: Request swagger UI
Given path 'q/swagger-ui/'
When method get
Then status 200

Feature: Test health is available

Background:
* url 'https://localhost:8443'

Scenario: Request health
Given path 'q/health'
When method get
Then status 200
And match $ == {"status":"UP","checks":[]}

Feature: Test the anonymous greeting endpoint

Background:
* url 'https://localhost:8443/api/v1'

Scenario: Request anonymous greeting
Given path 'anonymous/greeting'
When method get
Then status 200
And match $ == {greeting:'Hello, World!'}

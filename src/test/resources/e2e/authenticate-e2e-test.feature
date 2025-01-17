Feature: Test the authenticate endpoint

Background:
* url 'https://localhost:8443/api/v1'

Scenario: Request authenticate (Nominal case)
# Step to authenticate user and retrieve the token
Given path 'authenticate'
And request { username: 'admin', password: 'admin' }
When method post
Then status 200
And match $ == {token:'#string',type:'Bearer',expiresInSeconds:'#number? _ > 0'}

Scenario: Request authenticate (incorrect credentials)
# Step to authenticate user with incorrect credentials
Given path 'authenticate'
And request { username: 'admin', password: 'admin2' }
When method post
Then status 401

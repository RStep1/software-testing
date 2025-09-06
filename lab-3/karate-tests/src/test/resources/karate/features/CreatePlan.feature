Feature: Plan API - Create Plan Endpoint

Background:
  * url appBaseUrl + '/api/v1/create-plan'
  * configure headers = { 'Content-Type': 'application/json' }
  * def dbConfig = { url: '#(dbUrl)', username: '#(dbUsername)', password: '#(dbPassword)' }
  
  * def cleanupPlans = 
    """
    function(dbConfig) {
      var DbTestUtils = Java.type('com.rstep1.DbTestUtils');
      var db = new DbTestUtils(dbConfig.url, dbConfig.username, dbConfig.password);
      db.cleanTestData();
    }
    """
  * call cleanupPlans dbConfig

Scenario: Create plan with valid time range
  Given request
    """
    {
      "timeStart": "2023-01-01T00:00:00.000Z",
      "timeEnd": "2023-01-07T23:59:59.999Z"
    }
    """
  When method post
  Then status 200
  And match response contains { id: '#number' }

Scenario: Create plan with missing timeStart
  Given request 
    """
    {
      "timeEnd": "2023-01-07T23:59:59.999Z"
    }
    """
  When method post
  Then status 400

Scenario: Create plan with missing timeEnd
  Given request
    """
    {
      "timeStart": "2023-01-01T00:00:00.000Z"
    }
    """
  When method post
  Then status 400

Scenario: Create plan with invalid time format
  Given request 
    """
    {
      "timeStart": "invalid-date",
      "timeEnd": "2023-01-07T23:59:59.999Z"
    }
    """
  When method post
  Then status 500

Scenario: Create plan with timeEnd before timeStart
  Given request 
    """
    {
      "timeStart": "2023-01-07T00:00:00.000Z",
      "timeEnd": "2023-01-01T23:59:59.999Z"
    }
    """
  When method post
  Then status 500
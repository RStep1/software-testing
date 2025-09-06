Feature: Get Events Endpoint

Background:
  * url appBaseUrl + '/api/v1/get-events'
  * configure headers = { 'Content-Type': 'application/json' }
  * def dbConfig = { url: '#(dbUrl)', username: '#(dbUsername)', password: '#(dbPassword)' }
  
  * def setupTestData = 
    """
    function(dbConfig) {
      var DbTestUtils = Java.type('com.rstep1.DbTestUtils');
      var db = new DbTestUtils(dbConfig.url, dbConfig.username, dbConfig.password);
      
      db.cleanTestData();
      
      db.insertTestPlan(1, 'Test Vacation Plan');
      
      db.insertTestEvent(1, 'Meeting', 'Team meeting', 1672531200000, 1672534800000, 1); // Jan 1, 2023
      db.insertTestEvent(2, 'Lunch', 'Lunch break', 1672617600000, 1672621200000, 1);   // Jan 2, 2023
      db.insertTestEvent(3, 'Dinner', 'Dinner plans', 1675296000000, 1675299600000, 1);  // Feb 1, 2023 (outside range)
    }
    """
  * call setupTestData dbConfig

Scenario: Get events within valid date range
  Given request 
    """
    {
      "planId": 1,
      "dateStart": "2023-01-01T00:00:00.000Z",
      "dateEnd": "2023-01-03T00:00:00.000Z"
    }
    """
  When method post
  Then status 200
  And match response contains {timeEnd: 1672534800000, timeStart: 1672531200000, description: "Team meeting", id: 1, title: "Meeting"}
  And match response contains {timeEnd: 1672621200000, timeStart: 1672617600000, description: "Lunch break", id: 2, title: "Lunch"}
  And match response[*].title == ["Meeting", "Lunch"]

Scenario: Get events with no results in date range
  Given request 
    """
    {
      "planId": 1,
      "dateStart": "2023-03-01T00:00:00.000Z",
      "dateEnd": "2023-03-02T00:00:00.000Z"
    }
    """
  When method post
  Then status 200
  And match response == []

Scenario: Get events with invalid plan ID
  Given request 
    """
    {
      "planId": 999,
      "dateStart": "2023-01-01T00:00:00.000Z",
      "dateEnd": "2023-01-03T00:00:00.000Z"
    }
    """
  When method post
  Then status 400

Scenario: Get events with malformed dates
  Given request 
    """
    {
      "planId": 1,
      "dateStart": "invalid-date",
      "dateEnd": "2023-01-03T00:00:00.000Z"
    }
    """
  When method post
  Then status 500
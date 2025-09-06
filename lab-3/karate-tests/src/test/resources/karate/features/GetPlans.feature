Feature: Get Plans Endpoint

Background:
  * url appBaseUrl + '/api/v1/get-plans'
  * configure headers = { 'Content-Type': 'application/json' }
  * def dbConfig = { url: '#(dbUrl)', username: '#(dbUsername)', password: '#(dbPassword)' }
  
  * def setupTestData = 
    """
    function(dbConfig) {
      var DbTestUtils = Java.type('com.rstep1.DbTestUtils');
      var db = new DbTestUtils(dbConfig.url, dbConfig.username, dbConfig.password);
      
      db.cleanTestData();
      
      db.insertTestPlan(1, 'Vacation Plan');
      db.insertTestPlan(2, 'Work Schedule');
      db.insertTestPlan(3, 'Personal Goals');
    }
    """
  * call setupTestData dbConfig

Scenario: Get all plans when plans exist
  When method get
  Then status 200
  And match response == 
    """
    [
      { id: 1, title: 'Vacation Plan' },
      { id: 2, title: 'Work Schedule' },
      { id: 3, title: 'Personal Goals' }
    ]
    """
  And match response[*].id == [1, 2, 3]
  And match response[*].title contains ['Vacation Plan', 'Work Schedule', 'Personal Goals']

Scenario: Get plans when no plans exist
  * def cleanupAllPlans = 
    """
    function(dbConfig) {
      var DbTestUtils = Java.type('com.rstep1.DbTestUtils');
      var db = new DbTestUtils(dbConfig.url, dbConfig.username, dbConfig.password);
      db.cleanTestData();
    }
    """
  * call cleanupAllPlans dbConfig
  
  When method get
  Then status 200
  And match response == []
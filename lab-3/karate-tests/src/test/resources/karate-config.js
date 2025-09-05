function fn() {
    var env = karate.env || 'dev';
    karate.log('karate.env system property was:', env);
    
    var config = {
      env: env,
      appBaseUrl: java.lang.System.getProperty('app.base.url') || 'http://localhost:8080',
      dbUrl: java.lang.System.getProperty('db.url'),
      dbUsername: java.lang.System.getProperty('db.username'),
      dbPassword: java.lang.System.getProperty('db.password')
    };
    
    karate.configure('headers', {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });
    
    karate.configure('connectTimeout', 30000);
    karate.configure('readTimeout', 30000);
    
    return config;
  }
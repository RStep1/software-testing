package com.rstep1;

import java.time.Duration;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class TestContainersSetup {
    
    private static final String POSTGRES_IMAGE = "postgres:15-alpine";
    private static final String APP_IMAGE = "organiced:latest";
    
    protected static PostgreSQLContainer<?> postgres;
    protected static GenericContainer<?> app;
    
    protected static String appBaseUrl;
    protected static String dbUrl;
    protected static String dbUsername;
    protected static String dbPassword;
    
    @BeforeAll
    static void startContainers() {
        Network network = Network.newNetwork();

        postgres = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE))
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass")
                .withNetwork(network)
                .withNetworkAliases("postgres")
                .withExposedPorts(5432);
        
        postgres.start();
        
        dbUrl = postgres.getJdbcUrl();
        dbUsername = postgres.getUsername();
        dbPassword = postgres.getPassword();

        runFlywayMigrations();
                
        app = new GenericContainer<>(DockerImageName.parse(APP_IMAGE))
                .withExposedPorts(8080)
                .withNetwork(network)
                .withEnv("SPRING_DATASOURCE_URL", "jdbc:postgresql://postgres:5432/testdb")
                .withEnv("SPRING_DATASOURCE_USERNAME", dbUsername)
                .withEnv("SPRING_DATASOURCE_PASSWORD", dbPassword)
                .waitingFor(Wait.forLogMessage(".*Started.*", 1))
                .withStartupTimeout(Duration.ofSeconds(120));
           
        app.start();
        
        String host = app.getHost();
        int port = app.getFirstMappedPort();
        appBaseUrl = "http://" + host + ":" + port;
        
        System.setProperty("karate.env", "test");
        System.setProperty("app.base.url", appBaseUrl);
        System.setProperty("db.url", dbUrl);
        System.setProperty("db.username", dbUsername);
        System.setProperty("db.password", dbPassword);
    }

    private static void runFlywayMigrations() {
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(dbUrl, dbUsername, dbPassword)
                    .locations("classpath:db/migration")
                    .load();
            
            flyway.migrate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to run Flyway migrations", e);
        }
    }
    
    @AfterAll
    static void stopContainers() {
        if (app != null) {
            app.stop();
        }
        if (postgres != null) {
            postgres.stop();
        }
    }
}
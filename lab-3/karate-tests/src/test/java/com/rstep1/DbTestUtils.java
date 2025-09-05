package com.rstep1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DbTestUtils {
    
    private final String url;
    private final String username;
    private final String password;
    
    public DbTestUtils(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    public void cleanTestData() {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM event");
            stmt.executeUpdate("DELETE FROM plan");
            // stmt.executeUpdate("ALTER SEQUENCE event_id_seq RESTART WITH 1");
            // stmt.executeUpdate("ALTER SEQUENCE plan_id_seq RESTART WITH 1");
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean test data", e);
        }
    }
    
    public void insertTestPlan(Long id, String title) {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO plan (id, title) VALUES (?, ?)")) {
            stmt.setLong(1, id);
            stmt.setString(2, title);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert test plan", e);
        }
    }
    
    public void insertTestEvent(Long id, String title, String description, 
                               Long timeStart, Long timeEnd, Long planId) {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO event (id, title, description, time_start, time_end, plan_id) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setLong(1, id);
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.setLong(4, timeStart);
            stmt.setLong(5, timeEnd);
            stmt.setLong(6, planId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    // public void debugSchema() {
    //     try (Connection conn = DriverManager.getConnection(url, username, password);
    //          Statement stmt = conn.createStatement();
    //          ResultSet rs = stmt.executeQuery(
    //              "SELECT column_name, data_type FROM information_schema.columns " +
    //              "WHERE table_name = 'event' ORDER BY ordinal_position")) {
                
    //         System.out.println("Event table columns:");
    //         while (rs.next()) {
    //             System.out.println(rs.getString("column_name") + " - " + rs.getString("data_type"));
    //         }

    //     } catch (Exception e) {
    //         System.out.println("Error checking schema: " + e.getMessage());
    //     }
    // }
}
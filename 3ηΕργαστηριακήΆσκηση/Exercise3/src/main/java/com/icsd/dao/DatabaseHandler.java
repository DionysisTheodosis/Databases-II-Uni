package com.icsd.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:sqlite:attractionsDB.db";

    private Connection connection;

    public DatabaseHandler() {
        if (databaseFileExists()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(JDBC_URL);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException("Error establishing database connection", e);
            }
        } else {
            throw new RuntimeException("Database Not Exist");
        }
    }

    private boolean databaseFileExists() {
        File file = new File("attractionsDB.db");
        return file.exists();
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing database connection", e);
        }
    }
}

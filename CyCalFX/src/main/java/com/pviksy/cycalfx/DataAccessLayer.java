package com.pviksy.cycalfx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccessLayer {
    private Connection connection;

    public DataAccessLayer(String dbName) {
        openConnection(dbName);
    }

    private boolean openConnection(String databaseName) {
        String connectionString = "jdbc:jtds:sqlserver://localhost:1433/" + databaseName + ";instance=SQLEXPRESS;integratedSecurity=true";

        connection = null;

        try {
            System.out.println("Connecting to database...");

            // Load the JTDS driver (if not already loaded)
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            connection = DriverManager.getConnection(connectionString);

            System.out.println("Connected to database");

            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Could not connect to database!");
            System.out.println(e.getMessage());

            return false;
        }
    }

}
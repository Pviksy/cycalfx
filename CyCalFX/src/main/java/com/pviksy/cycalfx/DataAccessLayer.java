package com.pviksy.cycalfx;

import com.pviksy.cycalfx.Entities.Race;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class DataAccessLayer {

    Connection connection = null;

    public DataAccessLayer(String dbName) {
        openConnection(dbName);
    }

    private void openConnection(String databaseName) {
        String connectionString = "jdbc:jtds:sqlserver://localhost:1433/" + databaseName + ";instance=SQLEXPRESS;integratedSecurity=true";

        try {
            System.out.println("Connecting to database...");

            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            connection = DriverManager.getConnection(connectionString);

            System.out.println("Connected to database");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Could not connect to database!");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Race> getRaces(int month, int year) {
        try {
            ArrayList<Race> races = new ArrayList<>();

            String sql = "SELECT * FROM [race] WHERE [start_date] BETWEEN ? AND ? ORDER BY [start_date]";

            LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
            LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, java.sql.Date.valueOf(firstDayOfMonth));
            preparedStatement.setDate(2, java.sql.Date.valueOf(lastDayOfMonth));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category_id = resultSet.getString("category_id");
                String name = resultSet.getString("name");
                java.sql.Date startDate = resultSet.getDate("start_date");
                java.sql.Date endDate = resultSet.getDate("end_date");
                String logo = resultSet.getString("logo");
                String flag = resultSet.getString("flag");
                double distance = resultSet.getDouble("distance");
                String profileIcon = resultSet.getString("profile_icon");
                String profile = resultSet.getString("profile");

                races.add(new Race(id, category_id, name, startDate, endDate, logo, distance, profileIcon, profile));
            }

            return races;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get races.");
            System.out.println(e.getMessage());

            return null;
        }
    }

}
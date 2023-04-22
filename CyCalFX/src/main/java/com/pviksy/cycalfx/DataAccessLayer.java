package com.pviksy.cycalfx;

import com.pviksy.cycalfx.Entities.Race;
import com.pviksy.cycalfx.Service.ImageCache;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataAccessLayer {

    private Connection connection;

    public DataAccessLayer(String dbName) {
        openConnection(dbName);
    }

    private void openConnection(String databaseName) {

        String connectionString = "jdbc:jtds:sqlserver://localhost:1433/" + databaseName + ";integratedSecurity=true";

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

                races.add(new Race(id, category_id, name, startDate, endDate, logo, flag, distance, profileIcon, profile));
            }

            return races;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get races.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Race> getAllRaces() {
        ArrayList<Race> races = new ArrayList<>();
        races.addAll(getOneDayRaces());
        races.addAll(getStages());

        return races;
    }

    public ArrayList<Race> getOneDayRaces() {
        try {
            ArrayList<Race> races = new ArrayList<>();

            String sql = "SELECT * FROM [race] JOIN [category] ON [race].category_id = [category].id " +
                         "WHERE [start_date] = [end_date] ORDER BY [category].priority, [start_date]";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

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

                races.add(new Race(id, category_id, name, startDate, endDate, logo, flag, distance, profileIcon, profile));
            }

            return races;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get races.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ArrayList<Race> getStages() {
        try {
            ArrayList<Race> races = new ArrayList<>();

            String sql = "SELECT [stage].id,\n" +
                    "       [race].category_id,\n" +
                    "       [race].name,\n" +
                    "       [stage].number,\n" +
                    "       [stage].[date],\n" +
                    "       [race].logo, [race].flag,\n" +
                    "       [stage].distance,\n" +
                    "       [stage].profile_icon,\n" +
                    "       [stage].profile\n" +
                    "FROM [stage]\n" +
                    "JOIN [race] ON [stage].race_id = [race].id\n" +
                    "JOIN [category] ON [race].category_id = [category].id\n" +
                    "ORDER BY category.priority, [name], [stage].[number]";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category_id = resultSet.getString("category_id");
                String name = resultSet.getString("name");
                int number = resultSet.getInt("number");
                java.sql.Date date = resultSet.getDate("date");
                String logo = resultSet.getString("logo");
                String flag = resultSet.getString("flag");
                double distance = resultSet.getDouble("distance");
                String profileIcon = resultSet.getString("profile_icon");
                String profile = resultSet.getString("profile");

                races.add(new Race(id, category_id, "Stage " + number + " - " + name, date, date, logo, flag, distance, profileIcon, profile));
            }

            return races;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get stages.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public ImageCache loadImageCache() {
        try {
            ImageCache imageCache = new ImageCache();

            String sql = "SELECT logo, flag, profile_icon, profile FROM [race] WHERE [start_date] > '2022-01-01' ORDER BY [start_date]";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String logo = resultSet.getString("logo");
                String flag = resultSet.getString("flag");
                String profileIcon = resultSet.getString("profile_icon");
                String profile = resultSet.getString("profile");

                List<String> urls = Arrays.asList(logo, flag, "https://firstcycling.com/" + profileIcon, profile);
                urls = urls.stream().filter(Objects::nonNull).collect(Collectors.toList());
                imageCache.addAll(urls);
            }

            return imageCache;
        }
        catch (SQLException e) {
            System.out.println("Error: could not get imageURLs.");
            System.out.println(e.getMessage());

            return null;
        }
    }
}
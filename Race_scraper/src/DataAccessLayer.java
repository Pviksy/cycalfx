import java.sql.*;



public class DataAccessLayer {

    final static String dbName = "Betting" + Main.year;

    private Connection connection;

    public DataAccessLayer() {
        openConnection();
    }

    private boolean openConnection() {
        String connectionString =
                "jdbc:jtds:sqlserver://localhost:1433/" + dbName + "\";" +
                        "instanceName=SQLEXPRESS;" +
                        "databaseName=" + dbName + ";" +
                        "integratedSecurity=true;";

        connection = null;

        try {
            System.out.println("Connecting to database...");

            connection = DriverManager.getConnection(connectionString);

            System.out.println("Connected to database");

            return true;
        }
        catch (SQLException e) {
            System.out.println("Could not connect to database!");
            System.out.println(e.getMessage());

            return false;
        }
    }

    public void addRace(Race race) {
        try {
            String sql = "INSERT INTO race VALUES ('" + race.getName() + "', '" +
                                                        race.getCategory() + "', '" +
                                                        race.getClassification() + "', '" +
                                                        race.getCountry() + "', '" +
                                                        race.getDate() + "')"; //formatted date

            Statement statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 1) {
                ResultSet resultSet = statement.executeQuery("SELECT SCOPE_IDENTITY()");

                if (resultSet.next())
                    race.setRace_id(resultSet.getInt(1));
            }
        }
        catch (SQLException e) {
            System.out.println("Error: could not add race.");
            System.out.println(e.getMessage());
        }
    }

}

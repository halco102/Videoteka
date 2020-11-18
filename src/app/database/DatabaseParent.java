package app.database;

import java.sql.*;

public class DatabaseParent {

    protected ResultSet rs;
    protected Statement statement;
    protected Connection connection;
    protected PreparedStatement prepared;
    protected static ResultSet results;
    protected static Statement staticSt;


    DatabaseParent(String URL, String USER, String PASS){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }

        try { // Establish the database connection, create a statement for execution of SQL
            // commands.
            connection = DriverManager.getConnection(URL, USER, PASS); // username and password are passed into this
            // Constructor
            statement = connection.createStatement();
            staticSt = connection.createStatement(); // ukljucuje static statement
            System.out.println("Uspjesno povezano sa database");
        } catch (SQLException exception) {
            System.out.println("\n*** SQLException caught ***\n");
            while (exception != null) { // tell us the problem
                System.out.println("SQLState:    " + exception.getSQLState());
                System.out.println("Message:     " + exception.getMessage());
                System.out.println("Error code:  " + exception.getErrorCode());
                exception = exception.getNextException();

                System.out.println("");
            }
        } catch (java.lang.Exception exception) {
            exception.printStackTrace();
        }

    }

    public void closeDatabase() throws SQLException {
        statement.close();
        rs.close();
        connection.close();

    }


}

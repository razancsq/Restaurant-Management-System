
package aldiwan;

// this is the database connection Class.
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

// it contains the connection variables.
// to be used in all other classes.
public class DBConnection {
    public static Connection connection;

static{
    try{
        Properties props = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException(
                    "Missing db.properties. Copy db.properties.example to src/db.properties and fill in your database settings.");
            }
            props.load(input);
        }

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        // Initialize the connection
        connection = DriverManager.getConnection(url, username, password);
    }catch(SQLException e) {
        throw new RuntimeException("""
                                   Failed to establish database connection.
                                   Reason: """ +  e.getMessage());
    } catch (IOException e) {
        throw new RuntimeException("Failed to load db.properties: " + e.getMessage());
    } // try catch block// try catch{}
}

    // a method to retrieve the connection
    public static Connection getConnection() {
        return connection;
    }// getConnection()
} // DBConnection {}


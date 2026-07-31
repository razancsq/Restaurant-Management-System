
package aldiwan;

// this is the database connection Class. 
import java.sql.*;

// it contains the connection variables.
// to be used in all other classes.
public class DBConnection {
    public static Connection connection;
    public static final String URL = "jdbc:mysql://localhost:3306/aldiwandb";
    public static final String DBUser = "root";
    public static final String DBPassword = "ra2004";
        
static{    
    try{
        // Replace with your database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/aldiwandb";
        String username = "root";
        String password = "ra2004";

        // Initialize the connection
        connection = DriverManager.getConnection(url, username, password);
    }catch(SQLException e) {
        throw new RuntimeException("""
                                   Failed to establish database connection.
                                   Reason: """ +  e.getMessage());
    } // try catch block// try catch{}
}

    // a method to retrieve the connection
    public static Connection getConnection() {
        return connection;
    }// getConnection()
} // DBConnection {}


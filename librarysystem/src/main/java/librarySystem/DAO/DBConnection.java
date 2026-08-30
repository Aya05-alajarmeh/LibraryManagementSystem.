
package librarySystem.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=LibraryDB;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "";//Set your SQL Server password here 

    public static Connection getConnection() throws SQLException {

try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
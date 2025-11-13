package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://mail.tecnoweb.org.bo:5432/db_grupo22sc";
    private static final String USER = "grupo22sc";
    private static final String PASSWORD = "grup022grup022*";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver no encontrado", e);
        }
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
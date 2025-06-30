package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    // Cambia esta constante a "local" o "remoto" según lo necesites
    private static final String MODO_CONEXION = "local";

    // Configuración para conexión local
    private static final String LOCAL_URL = "jdbc:mysql://127.0.0.1:3306/dbVulcanizadora";
    private static final String LOCAL_USER = "root";
    private static final String LOCAL_PASSWORD = "rootpassword";

    // Configuración para conexión remota (AWS RDS)
    private static final String REMOTE_URL = "jdbc:mysql://database-1.crcmrpsxlrkt.us-east-1.rds.amazonaws.com:3306/dbVulcanizadorap";
    private static final String REMOTE_USER = "admin";
    private static final String REMOTE_PASSWORD = "23032007lu";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url, user, password;

                if (MODO_CONEXION.equalsIgnoreCase("local")) {
                    url = LOCAL_URL;
                    user = LOCAL_USER;
                    password = LOCAL_PASSWORD;
                } else {
                    url = REMOTE_URL;
                    user = REMOTE_USER;
                    password = REMOTE_PASSWORD;
                }

                // Driver opcional para Java 8+, pero se puede dejar para compatibilidad
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conexión establecida correctamente con " + MODO_CONEXION);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos.\n" + e.getMessage(),
                    "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            connection = null;
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Prueba de conexión exitosa!");
        } else {
            System.out.println("Prueba de conexión fallida.");
        }
    }
}



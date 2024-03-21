package quinielaservidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQL {

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/quiniela?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "root";

    // Método para obtener la conexión a la base de datos
    public Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Registrar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            // Manejar cualquier excepción que ocurra
            e.printStackTrace();
        }
        return conexion;
    }

    //METODO PARA CERRAR LA CONEXIÓN
    public void CerrarConecion(Connection cn) {
        try {
            cn.close();
        } catch (SQLException ex) {
        }
    }

}

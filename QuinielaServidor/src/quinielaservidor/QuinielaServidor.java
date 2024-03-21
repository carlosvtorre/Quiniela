package quinielaservidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;

public class QuinielaServidor {

    public static void main(String[] args) {
        ConexionSQL c = new ConexionSQL();
        Connection conexion = c.obtenerConexion();
        System.out.println("Conexión Exitosa");
        try {
            Registry registro = LocateRegistry.createRegistry(9000);//Arranco el servidor
            registro.rebind("Quiniela", new Metodos(conexion));//Instanciamos el objeto operaciones. Modificar y mandar la conexión a la base se datos

            System.out.println("Servidor corriendo....");

        } catch (Exception e) {
            System.out.println("Error el servidor no funciona....");
        }
    }

}

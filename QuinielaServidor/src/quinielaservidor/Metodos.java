package quinielaservidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import quinielainterfaz.Interfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Metodos extends UnicastRemoteObject implements Interfaz {

    //Variable para guardar la conexión
    Connection conexion = null;

    //Constructor que recibe la nonexión a la base de datos
    public Metodos(Connection conexion) throws RemoteException {
        this.conexion = conexion;
    }

    //Aqui Agregar los metodos
    public int iniciarSesion(String nombreUsuario, String password) throws RemoteException {
        // Verificar si el usuario existe
        boolean usuarioExiste = usuarioExiste(nombreUsuario);
        if (!usuarioExiste) {
            System.out.println("El nombre de usuario no existe.");
            return -1; // Nombre de usuario incorrecto
        }

        // Verificar si la contraseña es incorrecta
        boolean contraseñaIncorrecta = contraseñaIncorrecta(nombreUsuario, password);
        if (contraseñaIncorrecta) {
            System.out.println("La contraseña es incorrecta.");
            return -2; // Contraseña incorrecta
        }

        // Verificar el estatus y la actividad del usuario
        int estadoUsuario = obtenerEstadoUsuario(nombreUsuario);
        return estadoUsuario; // Devuelve el estado del usuario
    }

    private boolean usuarioExiste(String nombreUsuario) throws RemoteException {
        String sql = "SELECT COUNT(*) AS count FROM usuarios WHERE NombreUsuario = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombreUsuario);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // Devuelve true si el usuario existe en la base de datos
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar si el usuario existe: " + e.getMessage());
        }
        return false; // Error al verificar si el usuario existe
    }

    private boolean contraseñaIncorrecta(String nombreUsuario, String password) throws RemoteException {
        String sql = "SELECT COUNT(*) AS count FROM usuarios WHERE NombreUsuario = ? AND Password != ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombreUsuario);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // Devuelve true si la contraseña es incorrecta
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar si la contraseña es incorrecta: " + e.getMessage());
        }
        return false; // Error al verificar si la contraseña es incorrecta
    }

    private int obtenerEstadoUsuario(String nombreUsuario) throws RemoteException {
        String sql = "SELECT Estatus, Actividad FROM usuarios WHERE NombreUsuario = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombreUsuario);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int estatus = resultSet.getInt("Estatus");
                int actividad = resultSet.getInt("Actividad");
                if (estatus == 1 && actividad == 1) {
                    return 1; // Usuario administrador activo
                } else if (estatus == 0 && actividad == 1) {
                    return 2; // Usuario jugador activo
                } else {
                    return 0; // Usuario inactivo
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el estado del usuario: " + e.getMessage());
        }
        return -3; // Error al obtener el estado del usuario
    }

    public boolean registrarUsuario(String nombreUsuario, String password) throws RemoteException {
        // Verificar si el nombre de usuario ya existe
        if (usuarioExiste(nombreUsuario)) {
            System.out.println("El nombre de usuario ya existe.");
            return false;
        }

        String sql = "INSERT INTO usuarios (NombreUsuario, Password, Estatus, Actividad) VALUES (?, ?, 0, 0)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombreUsuario);
            statement.setString(2, password);
            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0; // Devuelve true si se insertó correctamente
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            return false;
        }
    }


    
    public boolean registrarEquipo(String nombreEquipo) throws RemoteException {
        String sql = "INSERT INTO equipos (nombre) VALUES (?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, nombreEquipo);
            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0; // Devuelve true si se insertó correctamente
        } catch (SQLException e) {
            System.out.println("Error al registrar el equipo: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> obtenerEquipos() throws RemoteException {
        ArrayList<String> equipos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM equipos";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                equipos.add(id + "@" + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los equipos: " + e.getMessage());
        }

        return equipos;
    }

    public ArrayList<String> obtenerUsuariosActivos() throws RemoteException {
    ArrayList<String> usuariosActivos = new ArrayList<>();
    String sql = "SELECT ID, NombreUsuario FROM usuarios WHERE Actividad = 1";
    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int idUsuario = resultSet.getInt("ID");
            String nombreUsuario = resultSet.getString("NombreUsuario");
            usuariosActivos.add(idUsuario + "@" + nombreUsuario);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener usuarios activos: " + e.getMessage());
    }
    return usuariosActivos;
}

public ArrayList<String> obtenerUsuariosInactivos() throws RemoteException {
    ArrayList<String> usuariosInactivos = new ArrayList<>();
    String sql = "SELECT ID, NombreUsuario FROM usuarios WHERE Actividad = 0";
    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int idUsuario = resultSet.getInt("ID");
            String nombreUsuario = resultSet.getString("NombreUsuario");
            usuariosInactivos.add(idUsuario + "@" + nombreUsuario);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener usuarios inactivos: " + e.getMessage());
    }
    return usuariosInactivos;
}



}

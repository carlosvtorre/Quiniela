package quinielaservidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import quinielainterfaz.Interfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Metodos extends UnicastRemoteObject implements Interfaz {

    //Variable para guardar la conexión
    Connection conexion = null;

    //Constructor que recibe la nonexión a la base de datos
    public Metodos(Connection conexion) throws RemoteException {
        this.conexion = conexion;
    }

    //METODOS DE MODULO USURIOS
    //
    //
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

    public boolean habilitarUsuario(int idUsuario) throws RemoteException {
        String sql = "UPDATE usuarios SET Actividad = 1 WHERE ID = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            System.out.println("Error al habilitar el usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean habilitarUsuarios(ArrayList<Integer> idsUsuarios) throws RemoteException {
        boolean exito = true;
        for (int id : idsUsuarios) {
            try {
                String sql = "UPDATE usuarios SET Actividad = 1 WHERE ID = ?";
                try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                    statement.setInt(1, id);
                    int filasActualizadas = statement.executeUpdate();
                    if (filasActualizadas == 0) {
                        System.out.println("No se actualizó ningún registro para el ID: " + id);
                        exito = false;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al habilitar el usuario con ID " + id + ": " + e.getMessage());
                exito = false;
            }
        }
        return exito;
    }

    //METODOS DE MODULO EQUIPOS
    //
    //
    public boolean registrarEquipo(String nombreEquipo) throws RemoteException {
        String sql = "INSERT INTO equipos (Nombre) VALUES (?)";
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
        String sql = "SELECT ID, nombre FROM equipos";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombre = resultSet.getString("Nombre");
                equipos.add(id + "@" + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los equipos: " + e.getMessage());
        }

        return equipos;
    }

    //       METODOS DEL MODULO JORNADAS Y PARTIPOS
    //
    //
    // Generar jornadas
    public boolean generarJornadas() throws RemoteException {
        List<Integer> equiposIDs = obtenerEquiposIDs();
        int numEquipos = equiposIDs.size();
        int numJornadas = numEquipos - 1;

        try {
            for (int i = 1; i <= numJornadas; i++) {
                int idJornada = insertarJornada("Jornada " + i, 1); // Estatus: 1 (predeterminado)

                for (int j = 0; j < numEquipos / 2; j++) {
                    int equipoLocalID = equiposIDs.get(j);
                    int equipoVisitanteID = equiposIDs.get(numEquipos - 1 - j);
                    insertarPartido(idJornada, equipoLocalID, equipoVisitanteID);
                }

                // Rotar los equipos para la siguiente jornada
                rotateTeams(equiposIDs);
            }
            return true; // Si llega hasta aquí, la generación de jornadas fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si ocurre un error, la generación de jornadas falla
        }
    }


    // Insertar jornada en la base de datos
    private int insertarJornada(String nombre, int estatus) {
        String sql = "INSERT INTO jornada (Nombre, Estatus) VALUES (?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, nombre);
            statement.setInt(2, estatus);
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas == 1) {
                return obtenerIDGenerado(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Insertar partido en la base de datos
    private void insertarPartido(int idJornada, int equipoLocalID, int equipoVisitanteID) throws SQLException {
        String sql = "INSERT INTO partidos (ID_Jornada, ID_Local, ID_Visitante) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idJornada);
            statement.setInt(2, equipoLocalID);
            statement.setInt(3, equipoVisitanteID);
            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas != 1) {
                throw new SQLException("Error al insertar el partido");
            }
        }
    }


    // Obtener IDs de todos los equipos
    public List<Integer> obtenerEquiposIDs() {
        List<Integer> equiposIDs = new ArrayList<>();
        String sql = "SELECT ID FROM equipos";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                equiposIDs.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equiposIDs;
    }

    // Rotar los equipos para la siguiente jornada
    private void rotateTeams(List<Integer> equiposIDs) {
        int ultimoID = equiposIDs.remove(equiposIDs.size() - 1);
        equiposIDs.add(1, ultimoID);
    }

    // Obtener el ID generado después de una inserción en la base de datos
    private int obtenerIDGenerado(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return -1;
    }

    
    public boolean hayJornadasAbiertas(){
        String sql = "SELECT COUNT(*) AS total FROM jornada WHERE Estatus = 1";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int total = resultSet.getInt("total");
                return total > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Si hay algún error o no se encontraron jornadas abiertas
    }
    
    // Método para obtener todas las jornadas abiertas
    public ArrayList<String> obtenerJornadasAbiertas() throws RemoteException {
        ArrayList<String> jornadasAbiertas = new ArrayList<>();
        String query = "SELECT ID, Nombre FROM jornada WHERE Estatus = 1";

        try (PreparedStatement statement = conexion.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idJornada = resultSet.getInt("ID");
                String nombreJornada = resultSet.getString("Nombre");
                String jornadaInfo = idJornada + "@" + nombreJornada;
                jornadasAbiertas.add(jornadaInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (lanzar o manejar de alguna manera)
        }

        return jornadasAbiertas;
    }

// Método para obtener todas las jornadas cerradas
    public ArrayList<String> obtenerJornadasCerradas() throws RemoteException {
        ArrayList<String> jornadasCerradas = new ArrayList<>();
        String query = "SELECT ID, Nombre FROM jornada WHERE Estatus = 0";

        try (PreparedStatement statement = conexion.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idJornada = resultSet.getInt("ID");
                String nombreJornada = resultSet.getString("Nombre");
                String jornadaInfo = idJornada + "@" + nombreJornada;
                jornadasCerradas.add(jornadaInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (lanzar o manejar de alguna manera)
        }

        return jornadasCerradas;
    }

}

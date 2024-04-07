package quinielainterfaz;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
//import java.rmi.RemoteException;

public interface Interfaz extends Remote{
    //Agregar los metodos de la clase Metodos del servidor aquí
    
    //TODOS PARTE ADMINISTRADOR
    public int iniciarSesion(String nombreUsuario, String password) throws RemoteException;//Metodo para validar el inicio de sesión
    public boolean registrarEquipo(String nombreEquipo) throws RemoteException;//Registrar nuevo equipo, return true si fue exitoso el registro si no false.
    public ArrayList<String> obtenerEquipos() throws RemoteException;//Obtener lista de todos los equipos registrados
    public ArrayList<String> obtenerUsuariosActivos() throws RemoteException;//Obtener lista de usuarios activos.
    public ArrayList<String> obtenerUsuariosInactivos() throws RemoteException;//Obtener lista de usuarios inactivos.
    public boolean registrarUsuario(String nombreUsuario, String password) throws RemoteException;//Registrar nuevo usuario.
    public boolean habilitarUsuario(int idUsuario) throws RemoteException;//Habilitar usuario
    public boolean habilitarUsuarios(ArrayList<Integer> idsUsuarios) throws RemoteException;//Habilitar todos los usuarios
    public boolean generarJornadas() throws RemoteException;//Metodo que genera las jornadas y los equipos con el metodo todos contra todos
    public boolean hayJornadasAbiertas() throws RemoteException;// este metodo devuelve true si hay jornadas abiertas si no hay o estan cerradas manda false
    public ArrayList<String> obtenerJornadasAbiertas() throws RemoteException;
    public ArrayList<String> obtenerJornadasCerradas() throws RemoteException;
    public ArrayList<String> obtenerPartidosPorJornada(int idJornada) throws RemoteException;//obtener los partidos de una jornada
    public void guardarResultados(ArrayList<String> resultados, int idJornada) throws RemoteException;
}

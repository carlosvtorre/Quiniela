package quinielainterfaz;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
//import java.rmi.RemoteException;

public interface Interfaz extends Remote{
    //Agregar los metodos de la clase Metodos del servidor aquí
    public int iniciarSesion(String nombreUsuario, String password) throws RemoteException;
    public boolean registrarEquipo(String nombreEquipo) throws RemoteException;//Registrar el equipo, return true si fue exitoso el registro si no false.
    public ArrayList<String> obtenerEquipos() throws RemoteException;
    public ArrayList<String> obtenerUsuariosActivos() throws RemoteException;
    public ArrayList<String> obtenerUsuariosInactivos() throws RemoteException;
    
}

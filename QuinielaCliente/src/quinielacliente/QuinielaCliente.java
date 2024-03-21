package quinielacliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import quinielainterfaz.Interfaz;

public class QuinielaCliente {

    public static void main(String[] args) {
        FVentana inicioSesion = new FVentana();
        inicioSesion.setVisible(true);
        inicioSesion.setLocationRelativeTo(null);
    }
    
    public void llamarRMI(){
        try {
            Registry registro = LocateRegistry.getRegistry("127.0.0.1", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            //resultadoSuma = calculadora.suma(num1, num2); 

        } catch (RemoteException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }
    
}

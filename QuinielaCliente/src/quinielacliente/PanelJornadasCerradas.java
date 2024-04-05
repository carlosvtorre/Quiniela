package quinielacliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import quinielainterfaz.Interfaz;

public class PanelJornadasCerradas extends javax.swing.JPanel {
DefaultTableModel modeloCerradas = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };
    
    public PanelJornadasCerradas() {
        initComponents();
        designTables();
        cargarJornadasCerradas();
    }
    public void designTables() {
        String[] encabezadosProductos = new String[]{"ID", "Jornadas Cerradas"};//Encabezados de las tablas
        modeloCerradas.setColumnIdentifiers(encabezadosProductos);//Se asignan los encabezados
        TablaCerradas.setModel(modeloCerradas);//se le asigna el modelo a la tabla dela interfaz 
    }
    
    public void cargarJornadasCerradas() {
        //metodo para cargar las jornadas abiertas
        try {
            Registry registro = LocateRegistry.getRegistry("127.0.0.1", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            ArrayList<String> jornadasCerradas = quiniela.obtenerJornadasCerradas();
            if (jornadasCerradas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay jornadas cerradas.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Limpiar el modelo de la tabla antes de agregar las nuevas jornadas
                modeloCerradas.setRowCount(0);
                // Agregar las jornadas abiertas al modelo de la tabla
                for (String jornadaInfo : jornadasCerradas) {
                    String[] partes = jornadaInfo.split("@");
                    modeloCerradas.addRow(new Object[]{partes[0], partes[1]});
                }
            }

        } catch (RemoteException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaCerradas = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TablaCerradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TablaCerradas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaCerradas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

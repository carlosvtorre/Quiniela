package quinielacliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import quinielainterfaz.Interfaz;

/**
 *
 * @author carlo
 */
public class PanelEquipos extends javax.swing.JPanel {

    DefaultTableModel modeloEquipos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };

    public PanelEquipos() {
        initComponents();
        designTables();
        CargarEquipos();
    }
    public void designTables() {
        String[] encabezadosProductos = new String[]{"ID", "Nombre Equipo"};//Encabezados de las tablas
        modeloEquipos.setColumnIdentifiers(encabezadosProductos);//Se asignan los encabezados
        TablaEquipos.setModel(modeloEquipos);//se le asigna el modelo a la tabla dela interfaz 
    }
    
    
    public boolean RegistrarEquipo(String nombreEquipo) {//metodo para registrar un equipo en la base de datos
        boolean registroValor = false;
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

             registroValor = quiniela.registrarEquipo(nombreEquipo);

        } catch (RemoteException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        return registroValor;
    }
    
    public void CargarEquipos() {
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            ArrayList<String> equipos = quiniela.obtenerEquipos();
            // Limpiar la tabla antes de agregar nuevos datos
            modeloEquipos.setRowCount(0);
            for (String equipo : equipos) {
                String[] datos = equipo.split("@");
                modeloEquipos.addRow(datos);
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
        TablaEquipos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        BtnRegistrarEquipo = new javax.swing.JButton();
        NombreEquipoTXT = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(710, 420));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(710, 420));

        TablaEquipos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TablaEquipos);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar Equipo"));

        BtnRegistrarEquipo.setText("Registrar Equipo");
        BtnRegistrarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistrarEquipoActionPerformed(evt);
            }
        });

        NombreEquipoTXT.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre del Equipo:"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NombreEquipoTXT)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(BtnRegistrarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(NombreEquipoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(BtnRegistrarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void BtnRegistrarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistrarEquipoActionPerformed
        // Validar si el campo de texto tiene un valor
        String nombreEquipo = NombreEquipoTXT.getText().trim();
        if (nombreEquipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un nombre de equipo.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
        } else {
            // Registrar equipo
            boolean resultado = RegistrarEquipo(nombreEquipo);
            if (resultado) {
                // El equipo se registró correctamente
                NombreEquipoTXT.setText("");//Limpio el campo de texto.
                CargarEquipos();
                JOptionPane.showMessageDialog(this, "Equipo registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Ocurrió un error al registrar el equipo
                JOptionPane.showMessageDialog(this, "Error al registrar equipo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_BtnRegistrarEquipoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnRegistrarEquipo;
    private javax.swing.JTextField NombreEquipoTXT;
    private javax.swing.JTable TablaEquipos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

package quinielacliente;

import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import quinielainterfaz.Interfaz;

/**
 *
 * @author carlo
 */
public class PanelUsuarios extends javax.swing.JPanel {
DefaultTableModel modeloActivos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };

DefaultTableModel modeloInactivos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };

    public PanelUsuarios() {
        initComponents();
        designTables();
        llenarTablasUsuarios();
    }
    
    public void llenarTablasUsuarios() {
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            ArrayList<String> UsuariosActivos = quiniela.obtenerUsuariosActivos();
            ArrayList<String> UsuariosInactivos = quiniela.obtenerUsuariosInactivos();

            // Limpiar las tablas antes de llenarlas
            modeloActivos.setRowCount(0);
            modeloInactivos.setRowCount(0);

            for (String usuario : UsuariosActivos) {
                String[] datos = usuario.split("@");
                modeloActivos.addRow(datos);
            }

            for (String usuario : UsuariosInactivos) {
                String[] datos = usuario.split("@");
                modeloInactivos.addRow(datos);
            }

        } catch (RemoteException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }
    
    public boolean HabilitarUsuario(int id) {
        boolean habilitar = false;
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");
            
            habilitar = quiniela.habilitarUsuario(id);
            

        } catch (RemoteException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        return habilitar;
    }
    public boolean HabilitarUsuarios() {
        boolean habilitar = false;
        ArrayList<Integer> IDLista = obtenerIdsUsuariosInactivos();
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");
            
            habilitar = quiniela.habilitarUsuarios(IDLista);
            

        } catch (RemoteException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        return habilitar;
    }
    private int obtenerIdUsuarioInactivo() {
        int id = -1; // Valor por defecto en caso de error
        int fila = TablaInactivos.getSelectedRow();
        if (fila != -1) {
            String idString = (String) modeloInactivos.getValueAt(fila, 0);
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el ID a entero: " + e.getMessage());
            }
        } else {
            System.out.println("No se ha seleccionado ninguna fila.");
        }
        return id;
    }
    
    public ArrayList<Integer> obtenerIdsUsuariosInactivos() {
        ArrayList<Integer> idsUsuariosInactivos = new ArrayList<>();
        // Verificar si la tabla tiene filas
        if (modeloInactivos.getRowCount() == 0) {
            System.out.println("La tabla de usuarios inactivos está vacía.");
            return idsUsuariosInactivos;
        }
        // Recorrer las filas de la tabla y obtener los IDs
        for (int i = 0; i < modeloInactivos.getRowCount(); i++) {
            String idString = (String) modeloInactivos.getValueAt(i, 0);
            try {
                int id = Integer.parseInt(idString);
                idsUsuariosInactivos.add(id);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el ID a entero: " + e.getMessage());
            }
        }
        return idsUsuariosInactivos;
    }




    public void designTables() {
        String[] encabezados = new String[]{"ID", "Nombre"};//Encabezados de las tablas
        
        modeloActivos.setColumnIdentifiers(encabezados);//Se asignan los encabezados
        modeloInactivos.setColumnIdentifiers(encabezados);//Se asignan los encabezados
        
        TablaActivos.setModel(modeloActivos);//se le asigna el modelo a la tabla dela interfaz 
        TablaInactivos.setModel(modeloInactivos);//se le asigna el modelo a la tabla dela interfaz 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopupInactivos = new javax.swing.JPopupMenu();
        Habilitar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaActivos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaInactivos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BtnHabilitarTodos = new javax.swing.JButton();

        Habilitar.setText("Habilitar Usuario");
        Habilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HabilitarActionPerformed(evt);
            }
        });
        PopupInactivos.add(Habilitar);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(710, 420));

        TablaActivos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TablaActivos);

        TablaInactivos.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaInactivos.setComponentPopupMenu(PopupInactivos);
        TablaInactivos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TablaInactivosMouseMoved(evt);
            }
        });
        jScrollPane2.setViewportView(TablaInactivos);

        jLabel1.setText("Jugadores Activos");

        jLabel2.setText("Jugadores Inactivos");

        BtnHabilitarTodos.setText("Habilitar Todos");
        BtnHabilitarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHabilitarTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(BtnHabilitarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(BtnHabilitarTodos))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
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

    private void HabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HabilitarActionPerformed
        // Habilitar Usuario
        int id = obtenerIdUsuarioInactivo();
        if(id == -1){
            System.out.println("no se pued obtener el id");
        }else{
            boolean habilitar = HabilitarUsuario(id);
            if(habilitar){
                llenarTablasUsuarios();
                System.out.println("Se habilito exitosamente");
            }else{
                System.out.println("Error al habilitar");
            }
        }
    }//GEN-LAST:event_HabilitarActionPerformed

    private void BtnHabilitarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHabilitarTodosActionPerformed
        // Habilitar todos los usuarios inactivos
        boolean habilitar;
        habilitar = HabilitarUsuarios();
        if(habilitar){
            llenarTablasUsuarios();
            System.out.println("Listo");
        }else{
            System.out.println("No se habilitaron usuarios");
        }          
    }//GEN-LAST:event_BtnHabilitarTodosActionPerformed

    private void TablaInactivosMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaInactivosMouseMoved
        // Seleccionar utomaticamente una fila cuando pasa el mouse por la tabla 
        // Obtener la fila en la que se encuentra el mouse
        Point p = evt.getPoint();
        int row = TablaInactivos.rowAtPoint(p);

        // Seleccionar la fila si el mouse está sobre ella
        if (row >= 0 && row < TablaInactivos.getRowCount()) {
            TablaInactivos.setRowSelectionInterval(row, row);
        }
    }//GEN-LAST:event_TablaInactivosMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnHabilitarTodos;
    private javax.swing.JMenuItem Habilitar;
    private javax.swing.JPopupMenu PopupInactivos;
    private javax.swing.JTable TablaActivos;
    private javax.swing.JTable TablaInactivos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}

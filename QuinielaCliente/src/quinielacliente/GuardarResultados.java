package quinielacliente;

import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import quinielainterfaz.Interfaz;

public class GuardarResultados extends javax.swing.JDialog {
    public int id;
    DefaultTableModel modeloPartidos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };
    DefaultTableModel modeloResultados = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };
    
    public GuardarResultados(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.id = id;
        designTables();
        cargarPartidosJornada();
    }
    
    public void designTables() {
        String[] encabezadosPartidos = new String[]{"ID","Equipo Local","Equipo Visitante"};//Encabezados de las tablas
        String[] encabezadosResultados = new String[]{"ID", "Equipo Local","Equipo Visitante","Ganador"};//Encabezados de las tablas
        modeloPartidos.setColumnIdentifiers(encabezadosPartidos);//Se asignan los encabezados
        modeloResultados.setColumnIdentifiers(encabezadosResultados);//Se asignan los encabezados
        TablaPartidos.setModel(modeloPartidos);//se le asigna el modelo a la tabla dela interfaz 
        TablaResultados.setModel(modeloResultados);
    }
    public void cargarPartidosJornada() {
        // Método para cargar los partidos de la jornada
        try {
            Registry registro = LocateRegistry.getRegistry("127.0.0.1", 9000);
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            // Obtener la lista de partidos por jornada
            ArrayList<String> partidos = quiniela.obtenerPartidosPorJornada(this.id);

            // Limpiar el modelo de la tabla de partidos antes de agregar los nuevos datos
            modeloPartidos.setRowCount(0);

            // Agregar los partidos al modelo de la tabla de partidos
            for (String partidoInfo : partidos) {
                String[] partes = partidoInfo.split("@");
                modeloPartidos.addRow(new Object[]{partes[0], partes[1], partes[2]});
            }

        } catch (RemoteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void agregarResultado(String ganador) {
        // Obtener el índice de la fila seleccionada en la tabla de partidos
        int selectedRow = TablaPartidos.getSelectedRow();

        // Obtener los datos de la fila seleccionada en la tabla de partidos
        Object[] rowData = new Object[modeloPartidos.getColumnCount()];
        for (int i = 0; i < modeloPartidos.getColumnCount(); i++) {
            rowData[i] = modeloPartidos.getValueAt(selectedRow, i);
        }

        // Agregar el ganador a los datos de la fila
        Object[] resultadoRowData = new Object[rowData.length + 1];
        System.arraycopy(rowData, 0, resultadoRowData, 0, rowData.length);
        resultadoRowData[rowData.length] = ganador;

        // Agregar los datos a la tabla de resultados
        modeloResultados.addRow(resultadoRowData);

        // Eliminar la fila seleccionada de la tabla de partidos
        modeloPartidos.removeRow(selectedRow);
    }

    public boolean tablaPartidosEstaVacia() {
        return modeloPartidos.getRowCount() == 0;
    }
    
    public ArrayList<String> obtenerResultados() {
        ArrayList<String> resultados = new ArrayList<>();

        for (int i = 0; i < modeloResultados.getRowCount(); i++) {
            String idPartido = (String) modeloResultados.getValueAt(i, 0);
            String ganador = (String) modeloResultados.getValueAt(i, 3);

            int valorGanador;
            switch (ganador) {
                case "Local":
                    valorGanador = 1;
                    break;
                case "Empate":
                    valorGanador = 2;
                    break;
                case "Visitante":
                    valorGanador = 3;
                    break;
                default:
                    valorGanador = 0; // Valor por defecto o para manejar errores
                    break;
            }

            resultados.add(idPartido + "@" + valorGanador);
        }

        return resultados;
    }
    
    public void GuardarResultadosPartidos() {
        try {
            Registry registro = LocateRegistry.getRegistry("127.0.0.1", 9000);
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");
            
            ArrayList<String> resultados = obtenerResultados();//se obtiene el id y resultado ejemplo id@1
            quiniela.guardarResultados(resultados, this.id);
        
        } catch (RemoteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Resultados = new javax.swing.JPopupMenu();
        Local = new javax.swing.JMenuItem();
        Empate = new javax.swing.JMenuItem();
        Visitante = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPartidos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaResultados = new javax.swing.JTable();
        BtnGuardarResultados = new javax.swing.JButton();

        Local.setText("Ganador Local");
        Local.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LocalActionPerformed(evt);
            }
        });
        Resultados.add(Local);

        Empate.setText("Empate");
        Empate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpateActionPerformed(evt);
            }
        });
        Resultados.add(Empate);

        Visitante.setText("Ganador Visitante");
        Visitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VisitanteActionPerformed(evt);
            }
        });
        Resultados.add(Visitante);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TablaPartidos.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaPartidos.setComponentPopupMenu(Resultados);
        TablaPartidos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TablaPartidosMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(TablaPartidos);

        jLabel1.setText("Guardar Resultados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        TablaResultados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(TablaResultados);

        BtnGuardarResultados.setText("Guardar Resultados");
        BtnGuardarResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarResultadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtnGuardarResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGuardarResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnGuardarResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarResultadosActionPerformed
        // Evento para guardar los resultados del partido
        if (tablaPartidosEstaVacia()) {
            System.out.println("la tabla partidos esta vacia");
            GuardarResultadosPartidos();
            this.setVisible(false);
            
        } else {
            System.out.println("La tabla partidos no esta vacia aun");
        }

    }//GEN-LAST:event_BtnGuardarResultadosActionPerformed

    private void TablaPartidosMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPartidosMouseMoved
        // Seleccionar utomaticamente una fila cuando pasa el mouse por la tabla 
        // Obtener la fila en la que se encuentra el mouse
        Point p = evt.getPoint();
        int row = TablaPartidos.rowAtPoint(p);

        // Seleccionar la fila si el mouse está sobre ella
        if (row >= 0 && row < TablaPartidos.getRowCount()) {
            TablaPartidos.setRowSelectionInterval(row, row);
        }
    }//GEN-LAST:event_TablaPartidosMouseMoved

    private void LocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LocalActionPerformed
        String ganador = "Local";
        agregarResultado(ganador);
    }//GEN-LAST:event_LocalActionPerformed

    private void EmpateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpateActionPerformed
        String ganador = "Empate";
        agregarResultado(ganador);
    }//GEN-LAST:event_EmpateActionPerformed

    private void VisitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VisitanteActionPerformed
        String ganador = "Visitante";
        agregarResultado(ganador);
    }//GEN-LAST:event_VisitanteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuardarResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuardarResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuardarResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuardarResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GuardarResultados dialog = new GuardarResultados(new javax.swing.JFrame(), true, 0);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardarResultados;
    private javax.swing.JMenuItem Empate;
    private javax.swing.JMenuItem Local;
    private javax.swing.JPopupMenu Resultados;
    private javax.swing.JTable TablaPartidos;
    private javax.swing.JTable TablaResultados;
    private javax.swing.JMenuItem Visitante;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}

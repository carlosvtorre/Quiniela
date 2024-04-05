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


public class PanelJornadasAbiertas extends javax.swing.JPanel {
DefaultTableModel modeloQuinielasAbiertas = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };
    
    public PanelJornadasAbiertas() {
        initComponents();
        designTables();
        cargarJornadasAbiertas();
    }
    public void designTables() {
        String[] encabezadosProductos = new String[]{"ID", "Jornadas Abiertas"};//Encabezados de las tablas
        modeloQuinielasAbiertas.setColumnIdentifiers(encabezadosProductos);//Se asignan los encabezados
        TablaQuinielasAbiertas.setModel(modeloQuinielasAbiertas);//se le asigna el modelo a la tabla dela interfaz 
    }

    public void cargarJornadasAbiertas() {
        //metodo para cargar las jornadas abiertas
        try {
            Registry registro = LocateRegistry.getRegistry("127.0.0.1", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            ArrayList<String> jornadasAbiertas = quiniela.obtenerJornadasAbiertas();
            if (jornadasAbiertas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay jornadas abiertas.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Limpiar el modelo de la tabla antes de agregar las nuevas jornadas
                modeloQuinielasAbiertas.setRowCount(0);
                // Agregar las jornadas abiertas al modelo de la tabla
                for (String jornadaInfo : jornadasAbiertas) {
                    String[] partes = jornadaInfo.split("@");
                    modeloQuinielasAbiertas.addRow(new Object[]{partes[0], partes[1]});
                }
            }

        } catch (RemoteException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ResultadosJornadas = new javax.swing.JPopupMenu();
        Resultados = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaQuinielasAbiertas = new javax.swing.JTable();

        Resultados.setText("Cargar Resultados");
        Resultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResultadosActionPerformed(evt);
            }
        });
        ResultadosJornadas.add(Resultados);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TablaQuinielasAbiertas.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaQuinielasAbiertas.setComponentPopupMenu(ResultadosJornadas);
        TablaQuinielasAbiertas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TablaQuinielasAbiertas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TablaQuinielasAbiertasMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(TablaQuinielasAbiertas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void TablaQuinielasAbiertasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaQuinielasAbiertasMouseMoved
        // Seleccionar utomaticamente una fila cuando pasa el mouse por la tabla 
        // Obtener la fila en la que se encuentra el mouse
        Point p = evt.getPoint();
        int row = TablaQuinielasAbiertas.rowAtPoint(p);

        // Seleccionar la fila si el mouse está sobre ella
        if (row >= 0 && row < TablaQuinielasAbiertas.getRowCount()) {
            TablaQuinielasAbiertas.setRowSelectionInterval(row, row);
        }
    }//GEN-LAST:event_TablaQuinielasAbiertasMouseMoved

    private void ResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResultadosActionPerformed
        // Popup menu resultados
        GuardarResultados g = new GuardarResultados(null, true);
        g.setVisible(true);
    }//GEN-LAST:event_ResultadosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Resultados;
    private javax.swing.JPopupMenu ResultadosJornadas;
    private javax.swing.JTable TablaQuinielasAbiertas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

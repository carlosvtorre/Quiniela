package quinielacliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import quinielainterfaz.Interfaz;

public class PanelJugarJonada extends javax.swing.JPanel {

    DefaultTableModel modeloPartidos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };

    DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };
    DefaultTableModel modeloTabla1 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int col) {//para que no se pueda editar la tabla
            return false;
        }
    };

    public PanelJugarJonada() throws NotBoundException {
        initComponents();
        llenarComboBoxJornadas();
        designPartidos();
        designPrediccion();

        // Agregar un listener al ComboBox para detectar cambios en la selección
        jCBoxJornadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jornadaSeleccionada = (String) jCBoxJornadas.getSelectedItem();
                mostrarPartidos(jornadaSeleccionada);
                mostrarPartidosJugados(jornadaSeleccionada);
                try {
                    cargarEquiposPartidoMinimo(jornadaSeleccionada);
                } catch (NotBoundException ex) {
                    Logger.getLogger(PanelJugarJonada.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        // Mostrar los partidos de la jornada inicialmente seleccionada
        String jornadaSeleccionada = (String) jCBoxJornadas.getSelectedItem();
        mostrarPartidos(jornadaSeleccionada);
        mostrarPartidosJugados(jornadaSeleccionada);
        cargarEquiposPartidoMinimo(jornadaSeleccionada);
    }

    public void designPartidos() {
        String[] encabezadosProductos = new String[]{"Local", "Visitante"};//Encabezados de las tablas
        modeloTabla.setColumnIdentifiers(encabezadosProductos);//Se asignan los encabezados
        tablaPartidos.setModel(modeloTabla);//se le asigna el modelo a la tabla dela interfaz 
    }

    public void designPrediccion() {
        String[] encabezadosProductos = new String[]{"Local", "Visitante", "Predicción"};//Encabezados de las tablas
        modeloTabla1.setColumnIdentifiers(encabezadosProductos);//Se asignan los encabezados
        TablaPredic.setModel(modeloTabla1);//se le asigna el modelo a la tabla dela interfaz 
    }

    public void llenarComboBoxJornadas() {
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            ArrayList<String> nombresJornadas = quiniela.obtenerJornadasActivas();

            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            for (String nombreJornada : nombresJornadas) {
                comboBoxModel.addElement(nombreJornada);
            }
            jCBoxJornadas.setModel(comboBoxModel);

        } catch (RemoteException e) {
            // Manejar la excepción adecuadamente
            e.printStackTrace();
        } catch (NotBoundException ex) {
            // Manejar la excepción adecuadamente
            ex.printStackTrace();
        }
    }

    // Método para mostrar los partidos de la jornada seleccionada en la tabla
    private void mostrarPartidos(String nombreJornada) {
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            ArrayList<String[]> equiposJornada = quiniela.obtenerEquiposPorJornada(nombreJornada);

            DefaultTableModel model = (DefaultTableModel) tablaPartidos.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            for (String[] equipo : equiposJornada) {
                model.addRow(new Object[]{equipo[0], equipo[1]});
            }

        } catch (RemoteException | NotBoundException e) {
            // Manejar la excepción adecuadamente
            e.printStackTrace();
        }
    }

    private void mostrarPartidosJugados(String nombreJornada) {
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            ArrayList<String[]> equiposJugados = quiniela.obtenerEquiposJugados(nombreJornada);

            DefaultTableModel model = (DefaultTableModel) TablaPredic.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            for (String[] equipo : equiposJugados) {
                model.addRow(new Object[]{equipo[0], equipo[1], equipo[2]});
            }

        } catch (RemoteException | NotBoundException e) {
            // Manejar la excepción adecuadamente
            e.printStackTrace();
        }
    }

    private void actualizarTablaPartidos() {
        String jornadaSeleccionada = (String) jCBoxJornadas.getSelectedItem();
        mostrarPartidos(jornadaSeleccionada);
        mostrarPartidosJugados(jornadaSeleccionada);
    }

    private void cargarEquiposPartidoMinimo(String nombreJornada) throws NotBoundException {
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");
            String[] equiposPartido = quiniela.obtenerEquiposPartidoMinimo(nombreJornada);
            if (equiposPartido != null && equiposPartido.length == 2) {
                EquipoLocal.setText(equiposPartido[0]);
                EquipoVisitante.setText(equiposPartido[1]);
            } else {
                // Manejo de error o mensaje de que no se encontraron equipos
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

// Método para enviar la predicción al servidor
    private void guardarPrediccion(String opcion) {
        try {
            Registry registro = LocateRegistry.getRegistry("192.168.0.152", 9000);
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            String equipoLocal = EquipoLocal.getText();
            String equipoVisitante = EquipoVisitante.getText();

            // Verificar si los campos de texto están vacíos
            if (equipoLocal.isEmpty() || equipoVisitante.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La jornada ha sido completada", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método si hay campos vacíos
            }

            // Llamar al método remoto para guardar la predicción
            quiniela.guardarPrediccion(equipoLocal, equipoVisitante, opcion);

            // Actualizar la tabla de partidos
            String jornadaSeleccionada = (String) jCBoxJornadas.getSelectedItem();
            mostrarPartidos(jornadaSeleccionada);

            // Cargar los equipos del partido mínimo
            cargarEquiposPartidoMinimo(jornadaSeleccionada);

        } catch (RemoteException | NotBoundException ex) {
            // Manejar la excepción adecuadamente
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jCBoxJornadas = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPartidos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaPredic = new javax.swing.JTable();
        EquipoLocal = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        PrediccionLocal = new javax.swing.JButton();
        PrediccionEmpate = new javax.swing.JButton();
        PrediccionVisitante = new javax.swing.JButton();
        EquipoVisitante = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccionar Jornada"));

        jCBoxJornadas.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de la Jornada"));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Partidos"));

        tablaPartidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Local", "Visitante"
            }
        ));
        jScrollPane2.setViewportView(tablaPartidos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCBoxJornadas, 0, 294, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jCBoxJornadas, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccionar de Predicciones"));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Predicciones de la jornada "));

        TablaPredic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Local", "Visitante", "Predicción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TablaPredic);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        );

        EquipoLocal.setBorder(javax.swing.BorderFactory.createTitledBorder("Local"));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Predicción del partido"));

        PrediccionLocal.setText("LOCAL");
        PrediccionLocal.setPreferredSize(new java.awt.Dimension(100, 23));
        PrediccionLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrediccionLocalActionPerformed(evt);
            }
        });

        PrediccionEmpate.setText("EMPATE");
        PrediccionEmpate.setPreferredSize(new java.awt.Dimension(100, 23));
        PrediccionEmpate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrediccionEmpateActionPerformed(evt);
            }
        });

        PrediccionVisitante.setText("VISITANTE");
        PrediccionVisitante.setPreferredSize(new java.awt.Dimension(100, 23));
        PrediccionVisitante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrediccionVisitanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(PrediccionLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(PrediccionEmpate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(PrediccionVisitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PrediccionLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrediccionVisitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrediccionEmpate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        EquipoVisitante.setBorder(javax.swing.BorderFactory.createTitledBorder("Visitante"));
        EquipoVisitante.setMinimumSize(new java.awt.Dimension(80, 22));
        EquipoVisitante.setPreferredSize(new java.awt.Dimension(80, 22));

        jLabel1.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        jLabel1.setText("VS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(EquipoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EquipoVisitante, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(EquipoVisitante, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(EquipoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void PrediccionLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrediccionLocalActionPerformed
        guardarPrediccion("1");
        actualizarTablaPartidos();
    }//GEN-LAST:event_PrediccionLocalActionPerformed

    private void PrediccionEmpateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrediccionEmpateActionPerformed
        guardarPrediccion("2");
        actualizarTablaPartidos();
    }//GEN-LAST:event_PrediccionEmpateActionPerformed

    private void PrediccionVisitanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrediccionVisitanteActionPerformed
        guardarPrediccion("3");
        actualizarTablaPartidos();
    }//GEN-LAST:event_PrediccionVisitanteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EquipoLocal;
    private javax.swing.JTextField EquipoVisitante;
    private javax.swing.JButton PrediccionEmpate;
    private javax.swing.JButton PrediccionLocal;
    private javax.swing.JButton PrediccionVisitante;
    private javax.swing.JTable TablaPredic;
    private javax.swing.JComboBox<String> jCBoxJornadas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaPartidos;
    // End of variables declaration//GEN-END:variables
}

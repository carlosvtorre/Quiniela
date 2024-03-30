
package quinielacliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import quinielainterfaz.Interfaz;

public class FVentana extends javax.swing.JFrame {

    public FVentana() {
        initComponents();
        placeholder();
        
    }
 
    public void placeholder() {
        TextPrompt prueba = new TextPrompt("Ingrese Su Contraseña", PasswordTXT);
        TextPrompt prueb = new TextPrompt("Ingrese Su Usuario", NombreTXT);
    }
    
    public int IniciarSesion(String nombreUsuario, String password) {
        int sesion = 7;
        try {
            Registry registro = LocateRegistry.getRegistry("127.0.0.1", 9000);// "127.0.0.1"=localhost, la ip del servidor/ puerto por el que se comunican
            Interfaz quiniela = (Interfaz) registro.lookup("Quiniela");

            sesion = quiniela.iniciarSesion(nombreUsuario, password);

        } catch (RemoteException e) {
            System.out.println("Error" + e.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        return sesion;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        BtnIniciarSesion = new javax.swing.JButton();
        NombreTXT = new javax.swing.JTextField();
        PasswordTXT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        BtnRegistrarse = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtnIniciarSesion.setText("Iniciar Sesión");
        BtnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIniciarSesionActionPerformed(evt);
            }
        });

        NombreTXT.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuario:"));

        PasswordTXT.setBorder(javax.swing.BorderFactory.createTitledBorder("Contraseña:"));

        jLabel2.setText("No tienes Cuenta?");

        BtnRegistrarse.setText("Registrarse");
        BtnRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistrarseActionPerformed(evt);
            }
        });

        jButton3.setText("Agregar icono aquí");

        jLabel3.setText("Iniciar sesión en la quiniela");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BtnIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NombreTXT)
                            .addComponent(PasswordTXT, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jButton3)
                .addGap(44, 44, 44)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(NombreTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(PasswordTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(BtnIniciarSesion)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(BtnRegistrarse))
                .addGap(97, 97, 97))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 580));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancha1.jpeg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 580));

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

    private void BtnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIniciarSesionActionPerformed
        // Evento para iniciar sesión
        String nombre = NombreTXT.getText().trim();//se obtienen los datos
        String password = PasswordTXT.getText().trim();

        int sesion = IniciarSesion(nombre, password);//metodo para iniciar sesion / 
        switch (sesion) {
            case -1:
                // Mostrar mensaje de nombre de usuario incorrecto
                JOptionPane.showMessageDialog(this, "Nombre de usuario incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case -2:
                // Mostrar mensaje de contraseña incorrecta
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case 0:
                // Mostrar mensaje de usuario inactivo
                JOptionPane.showMessageDialog(this, "El usuario está inactivo", "Inactivo", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 1:
                // Iniciar sesión como administrador
                MainAdmin admin = new MainAdmin(this, true);
                this.dispose();
                admin.setVisible(true);
                
                break;
            case 2:
                // Iniciar sesión como jugador
                MainJugador jugador = new MainJugador(this, true);
                this.dispose();
                jugador.setVisible(true);
                
                 break;
            default:
                // Mostrar mensaje de error desconocido
                
                break;
        }

    }//GEN-LAST:event_BtnIniciarSesionActionPerformed

    private void BtnRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistrarseActionPerformed
        // Crear usuario
        Registrarse r = new Registrarse(this, true);
        r.setVisible(true);
        
    }//GEN-LAST:event_BtnRegistrarseActionPerformed

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
            java.util.logging.Logger.getLogger(FVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FVentana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FVentana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnIniciarSesion;
    private javax.swing.JButton BtnRegistrarse;
    private javax.swing.JTextField NombreTXT;
    private javax.swing.JTextField PasswordTXT;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}

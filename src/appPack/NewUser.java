package appPack;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class NewUser extends javax.swing.JFrame {
    DB db = new DB();
    public NewUser() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        txtNewUName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtNewUPassword = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNewUPassword2 = new javax.swing.JTextField();
        btnAddNewUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(490, 320));
        setPreferredSize(new java.awt.Dimension(490, 320));

        jLabel13.setBackground(new java.awt.Color(153, 153, 153));
        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setText("Kullanıcı Adı :");

        jLabel15.setBackground(new java.awt.Color(153, 153, 153));
        jLabel15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel15.setText("Şifre :");

        jLabel16.setBackground(new java.awt.Color(153, 153, 153));
        jLabel16.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel16.setText("Şifre Tekrar :");

        btnAddNewUser.setBackground(new java.awt.Color(255, 255, 255));
        btnAddNewUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/newadmin.png"))); // NOI18N
        btnAddNewUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNewUName, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNewUPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtNewUPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(btnAddNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewUName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewUPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewUPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(btnAddNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewUserActionPerformed
        String userName = txtNewUName.getText().trim();
        String userPassword = txtNewUPassword.getText().trim();
        String userPassword2 = txtNewUPassword2.getText().trim();
        if(userName.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Kullanıcı Adı Giriniz!");
            txtNewUPassword.setText("");
            txtNewUPassword2.setText("");
            txtNewUName.requestFocus();
        }else if(userPassword.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Şifre Giriniz!");
            txtNewUPassword.setText("");
            txtNewUPassword2.setText("");
            txtNewUPassword.requestFocus();
        }else if(userPassword2.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Şifreyi Tekrar Giriniz!");
            txtNewUPassword.setText("");
            txtNewUPassword2.setText("");
            txtNewUPassword2.requestFocus();
        }else if(!userPassword.equals(userPassword2)){
            JOptionPane.showMessageDialog(rootPane, "Şifreler Uyuşmamaktadır!");
            txtNewUPassword.setText("");
            txtNewUPassword2.setText("");
            txtNewUPassword.requestFocus();
        }else{
            String query = "SELECT * FROM `users` WHERE `userName` = '"+userName+"'";
            try {
                ResultSet rs = db.baglan().executeQuery(query);
                if(rs.next()){
                    JOptionPane.showMessageDialog(rootPane,"Bu Kullanıcı Adı Kullanılmaktadır, Lütfen Farklı Kullanıcı Adı Giriniz!");
                    txtNewUName.setText("");
                    txtNewUPassword.setText("");
                    txtNewUPassword2.setText("");
                    txtNewUName.requestFocus();
                }else{
                    int ekle = db.baglan().executeUpdate("INSERT INTO `users` (`userID`, `userName`, `userPassword`) VALUES (NULL, '"+userName+"', '"+userPassword+"');");
                    if(ekle > 0){
                        JOptionPane.showMessageDialog(rootPane,"Kullanıcı Eklendi.");
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(rootPane,"UKullanıcı Eklenemedi!");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Database Bağlantı Hatası!");
            }
        }
    }//GEN-LAST:event_btnAddNewUserActionPerformed

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
            java.util.logging.Logger.getLogger(NewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewUser;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JTextField txtNewUName;
    private javax.swing.JTextField txtNewUPassword;
    private javax.swing.JTextField txtNewUPassword2;
    // End of variables declaration//GEN-END:variables
}

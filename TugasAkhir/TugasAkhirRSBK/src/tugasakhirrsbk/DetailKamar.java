/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugasakhirrsbk;

import TabelData.DataKamar;
import TabelData.DataUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author Rayc
 */
public class DetailKamar extends javax.swing.JFrame {

    /**
     * Creates new form DetailKamar
     */
    DataKamar setNilai;
    dbconnections c;
    String cekA;
    DataUser session;
    
    
    public DetailKamar() {
        initComponents();
    }
    
    public DetailKamar(DataKamar a, DataUser b) {
        initComponents();
        c = new dbconnections();
        this.setResizable(false);
        setNilai = a;
        session = b;
        if (session.getRole().equals("Member")){
            btnEdit.setVisible(false);
        }
        this.setTitle(a.getId_kamar());
        fieldKamar.setText(a.getId_kamar());
        fieldKelas.setText(a.getKelas());
        fieldHarga.setText(Integer.toString(a.getHarga()));
        fieldFasilitas.setText(a.getFasilitas());
    }
    
    private int cekHarga(){
        try{
        SimpleDateFormat date = new SimpleDateFormat ("dd/MM/yyy");
        String tanggal1 = String.valueOf(date.format(txtTgl1.getDate()));
        Date TglCekIn = (Date) date.parse(tanggal1);
        
        String tanggal2 = String.valueOf(date.format(txtTgl2.getDate()));
        Date TglCekOut = (Date) date.parse(tanggal2);
        long Telat = Math.abs(TglCekOut.getTime() - TglCekIn.getTime());
        long jj = (TimeUnit.MILLISECONDS.toDays(Telat)+1)*setNilai.getHarga();
        
        hargaTotal.setText(""+ jj);
        return (int) jj;
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }
    
    private String ubahTgl(String tgl){
        try{
        SimpleDateFormat date = new SimpleDateFormat ("dd/MM/yyy");
        Date TglPinjam = (Date) date.parse(tgl);
        Calendar day = Calendar.getInstance();
        day.setTime(TglPinjam);
        day.add(Calendar.DATE, 1);
        Date modifiedDate = day.getTime();
        String tglbaru = String.valueOf(date.format(modifiedDate));
        return tglbaru;
        }
        catch (Exception e){
            System.out.println(e);
            return "";
        }
    }
    
    private int cekJml(){
        try{
        SimpleDateFormat date = new SimpleDateFormat ("dd/MM/yyy");
        String tanggal1 = String.valueOf(date.format(txtTgl1.getDate()));
        Date TglCekIn = (Date) date.parse(tanggal1);
        
        String tanggal2 = String.valueOf(date.format(txtTgl2.getDate()));
        Date TglCekOut = (Date) date.parse(tanggal2);
        long Telat = Math.abs(TglCekOut.getTime() - TglCekIn.getTime());
        long jj = TimeUnit.MILLISECONDS.toDays(Telat);
        
        return (int) jj;
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }
    
    private boolean cekKamar(String kamar, String tgl){
        try {
            String sql="SELECT status from kondisi where id_kamar='"+kamar+"' AND tgl ='"+tgl+"'";
            ResultSet rsShow = c.script.executeQuery(sql);
            while (rsShow.next()){
                String cekB = rsShow.getString(1);
                if(cekB.equals("Booked")||cekB.equals("CekIn")||cekB.equals("CekOut")){
                    return true;
                }
            }
        }
        catch(Exception e){
            System.err.print(e);
            return false;
        }
        return false;
    }
    
    private boolean cekKetersediaan(){
        SimpleDateFormat date = new SimpleDateFormat ("dd/MM/yyy");
        String tglawal = String.valueOf(date.format(txtTgl1.getDate()));
        Boolean stat2=false;
        int n = cekJml();
        for(int i = 0; i<=n; i++){
            if (!stat2){
                stat2 = cekKamar(fieldKamar.getText(), tglawal);
                tglawal = ubahTgl(tglawal);
            }
            else{
                return stat2;
            }
        }
        return stat2;
    }
    
    private void insertBooking(){
        SimpleDateFormat date = new SimpleDateFormat ("dd/MM/yyy");
        String tglawal = String.valueOf(date.format(txtTgl1.getDate()));
        String tglakhir = String.valueOf(date.format(txtTgl2.getDate()));
        int n = cekJml();
        for(int i = 0; i<=n; i++){
            try{
                //For MySql
                String sql1 = "INSERT INTO KONDISI values (null ,'"+setNilai.getId_kamar()+"' , '" + tglawal + "','Booked')";
                c.script.executeUpdate(sql1);
                tglawal = ubahTgl(tglawal);
            }catch(SQLException e){
                System.err.print(e);
            }
        }
        try{
                //For MySql
                String sql1 = "INSERT INTO SEWA values (null, '"+setNilai.getId_kamar()+"' , '" + String.valueOf(date.format(txtTgl1.getDate())) + "', '" + String.valueOf(date.format(txtTgl2.getDate())) + "', '" + session.getUsername() + "', '"+cekHarga()+"', 'Booked')";
                c.script.executeUpdate(sql1);
                JOptionPane.showMessageDialog(this, "Data Berhasil Ditambahkan!");
        }
        
        catch(SQLException e){
                System.err.print(e);
                JOptionPane.showMessageDialog(this, "Terjadi Kesalahan");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fieldKamar = new javax.swing.JTextField();
        fieldKelas = new javax.swing.JTextField();
        fieldHarga = new javax.swing.JTextField();
        fieldFasilitas = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        txtTgl1 = new com.toedter.calendar.JDateChooser();
        txtTgl2 = new com.toedter.calendar.JDateChooser();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        hargaTotal = new java.awt.Label();
        btnSubmit = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID Kamar");

        jLabel2.setText("Kelas");

        jLabel3.setText("Harga Permalam");

        jLabel4.setText("Fasilitas");

        fieldKamar.setText("jTextField1");

        fieldKelas.setText("jTextField2");

        fieldHarga.setText("jTextField3");

        fieldFasilitas.setText("-");

        label1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label1.setText("BOOKING KAMAR");

        label2.setText("Tanggal Cek In");

        label3.setText("Tanggal Cek Out");

        label4.setText("Estimasi Biaya");

        hargaTotal.setText("-");

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jButton2.setText("Cek Harga");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit Kamar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hargaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldKamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldFasilitas))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTgl1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTgl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                        .addGap(19, 19, 19)))
                .addGap(46, 46, 46))
            .addGroup(layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fieldKamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fieldKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fieldHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fieldFasilitas))
                .addGap(20, 20, 20)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTgl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTgl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hargaTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(10, 10, 10)
                .addComponent(btnSubmit)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cekHarga();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        Boolean cek;
        cek = cekKetersediaan();
        if (cek){
            JOptionPane.showMessageDialog(this, "Kamar "+setNilai.getId_kamar()+"pada Tanggal Tersebut Sudah di Tempati");
        }
        else {
            int jawab = JOptionPane.showConfirmDialog(this, "Yakin Sudah Benar?");
    
            switch(jawab){
                case JOptionPane.YES_OPTION: 
                    insertBooking();
                break;
                case JOptionPane.NO_OPTION:
                break;
        }
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            ListKamar lK = new ListKamar(session);
            lK.setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(DetailKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetailKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetailKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetailKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetailKamar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel fieldFasilitas;
    private javax.swing.JTextField fieldHarga;
    private javax.swing.JTextField fieldKamar;
    private javax.swing.JTextField fieldKelas;
    private java.awt.Label hargaTotal;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private com.toedter.calendar.JDateChooser txtTgl1;
    private com.toedter.calendar.JDateChooser txtTgl2;
    // End of variables declaration//GEN-END:variables
}

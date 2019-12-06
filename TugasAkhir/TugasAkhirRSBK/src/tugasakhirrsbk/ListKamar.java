/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugasakhirrsbk;

import TabelData.DataKamar;
import TabelData.DataSewa;
import TabelData.DataUser;
import TabelData.TabelDataSewa;
import java.awt.Color;
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
public class ListKamar extends javax.swing.JFrame {
    private String status, tanggal1;
    dbconnections c;
    SimpleDateFormat date = new SimpleDateFormat ("dd/MM/yyy");
    DataUser session;
    TabelDataSewa tdSewa;
    String tanggalnyaMasuk, tanggalnyaKeluar;
    private int hargatot;
    private String statnya;
    /**F
     * Creates new form ListKamar
     */
    public ListKamar() {
        initComponents();
        c = new dbconnections();
        Date today = new Date();
        txtTanggal1.setDate(today);
        tanggal1 = String.valueOf(date.format(txtTanggal1.getDate()));
        loadKamar(tanggal1);
    }
    
    public ListKamar(DataUser session){
        initComponents();
        tdSewa = new TabelDataSewa();
        tabelSewa.setModel(tdSewa);
        this.session = session;
        if(session.getRole().equals("Admin")){
            viewAdmin();
        }
        else{
            viewMember();
        }
        txtNama.setText(this.session.getNama());
        saldo.setText("Rp."+this.session.getSaldo());
        c = new dbconnections();
        Date today = new Date();
        txtTanggal1.setDate(today);
        tanggal1 = String.valueOf(date.format(txtTanggal1.getDate()));
        loadKamar(tanggal1);
        Tampil();
        this.setTitle("Sistem Booking Penginapan");
    }
    
    private void viewAdmin(){
        btEdit.setVisible(false);
        btBatal.setVisible(false);
        btLunas.setVisible(false);
        btCO.setEnabled(false);
        btCI.setEnabled(false);
        btApp.setEnabled(false);
    }
    
    private void viewMember(){
        btCO.setVisible(false);
        btCI.setVisible(false);
        btApp.setVisible(false);
        btEdit.setEnabled(false);
        btBatal.setEnabled(false);
        btLunas.setEnabled(false);
    }
    
    private void Tampil(){
        try {
            int row = tabelSewa.getRowCount();
            System.out.println("cek ="+row);
            for(int i=0;i<row;i++){
                tdSewa.delete(0, row);
            }
            String sql;
            if (session.getRole().equals("Admin")){
            sql="SELECT id_kamar,tgl_masuk,tgl_keluar,username,hargatotal,status FROM sewa";
            }
            else {
            sql="SELECT id_kamar,tgl_masuk,tgl_keluar,username,hargatotal,status FROM sewa WHERE username ='"+session.getUsername()+"'";
            }
            ResultSet rsShow = c.script.executeQuery(sql);
            while(rsShow.next()){
                DataSewa d = new DataSewa();
                d.setId_kamar(rsShow.getString("ID_KAMAR"));
                d.setTgl_masuk(rsShow.getString("TGL_MASUK"));
                d.setTgl_keluar(rsShow.getString("TGL_KELUAR"));
                d.setUsername(rsShow.getString("USERNAME"));
                d.setHargatotal(rsShow.getInt("HARGATOTAL"));
                d.setStatus(rsShow.getString("STATUS"));
                tdSewa.add(d);
            }
        }catch(Exception e){
            System.err.print(e);
        }
    } 
    
    public String showData(DataSewa dm){
        try{
            txtKamar.setText(dm.getId_kamar());
            txtPenyewa.setText(dm.getUsername());
            txtBiaya.setText ("Rp."+dm.getHargatotal());
            txtStatus2.setText (dm.getStatus());
            Date tgM = (Date) date.parse(dm.getTgl_masuk());
            msk.setDate(tgM);
            Date tgK = (Date) date.parse(dm.getTgl_keluar());
            klr.setDate(tgK);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return dm.getTgl_masuk();
    }
    
    public void loadKamar(String tanggal1){
        kamar1.cekTersedia("KK-01", tanggal1);
        kamar2.cekTersedia("KK-02", tanggal1);
        kamar3.cekTersedia("KK-03", tanggal1);
        kamar4.cekTersedia("KK-04", tanggal1);
        kamar5.cekTersedia("KK-05", tanggal1);
        kamar6.cekTersedia("KV-01", tanggal1);
        kamar7.cekTersedia("KV-02", tanggal1);
        kamar8.cekTersedia("KV-03", tanggal1);
        kamar9.cekTersedia("KV-04", tanggal1);
        kamar10.cekTersedia("KV-05",tanggal1);
        kamar11.cekTersedia("KE-01",tanggal1);
        kamar12.cekTersedia("KE-02",tanggal1);
        kamar13.cekTersedia("KE-03",tanggal1);
        kamar14.cekTersedia("KE-04",tanggal1);
        kamar15.cekTersedia("KE-05",tanggal1);
    }
    
    private DataKamar tfKamar(String id){
        try{
            String sql="SELECT * from kamar WHERE id_kamar ='"+id+"'";
            ResultSet rsShow = c.script.executeQuery(sql);
            while(rsShow.next()){
                DataKamar d = new DataKamar();
                d.setId_kamar(rsShow.getString("ID_KAMAR"));
                d.setKelas(rsShow.getString("KELAS"));
                d.setHarga(rsShow.getInt("HARGA"));
                d.setFasilitas(rsShow.getString("FASILITAS"));
                d.setStatus(rsShow.getString("STATUS"));
                return d;
            }
        }
        catch(Exception e){
            System.err.print(e);
            return null;
        }
        return null;
    }

    private void lunasin(){
        try{
            int sisa = session.getSaldo()-hargatot;
            String sql = "UPDATE sewa SET status = 'Lunas' where id_kamar='"+txtKamar.getText()+"' AND tgl_masuk ='"+tanggalnyaMasuk+"'";
            c.script.executeUpdate(sql);
            String sql2 = "UPDATE user SET saldo="+sisa+" where username='"+session.getUsername()+"'";
            c.script.executeUpdate(sql2);
            
            txtStatus2.setText("Lunas");
            saldo.setText("Rp."+sisa);
            session.setSaldo(sisa);
            Tampil();
            
            JOptionPane.showMessageDialog(null, "Pembayaran Berhasil");
            }
        	catch(SQLException ex){
        	System.err.print(ex);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
        }
    }
    
    private String ubahTgl(String tgl){
        try{
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
    
    private int cekJml(String tgl1, String tgl2){
        try{

        Date TglCekIn = (Date) date.parse(tgl1);
        Date TglCekOut = (Date) date.parse(tgl2);
        long Telat = Math.abs(TglCekOut.getTime() - TglCekIn.getTime());
        long jj = TimeUnit.MILLISECONDS.toDays(Telat);
        
        return (int) jj;
        }
        catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }
    
        private int cekHarga(){
        try{
        DataKamar d = new DataKamar();
        String t1 = String.valueOf(date.format(msk.getDate()));
        Date TglCekIn = (Date) date.parse(t1);
        String t2 = String.valueOf(date.format(klr.getDate()));
        Date TglCekOut = (Date) date.parse(t2);
        long Telat = Math.abs(TglCekOut.getTime() - TglCekIn.getTime());
        String sql="SELECT * from kamar WHERE id_kamar ='"+txtKamar.getText()+"'";
        ResultSet rsShow = c.script.executeQuery(sql);
        while(rsShow.next()){
                d.setId_kamar(rsShow.getString("ID_KAMAR"));
                d.setKelas(rsShow.getString("KELAS"));
                d.setHarga(rsShow.getInt("HARGA"));
                d.setFasilitas(rsShow.getString("FASILITAS"));
                d.setStatus(rsShow.getString("STATUS"));
        }
        
        long jj = (TimeUnit.MILLISECONDS.toDays(Telat)+1)*d.getHarga();
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
    
    private boolean cekKetersediaan(String awal, String akhir){
        String tglawal = awal;
        Boolean stat2=false;
        int n = cekJml(awal, akhir);
        for(int i = 0; i<=n; i++){
            if (!stat2){
                stat2 = cekKamar(txtKamar.getText(), tglawal);
                tglawal = ubahTgl(tglawal);
            }
            else{
                return stat2;
            }
        }
        return stat2;
    }
    
    private void insertBooking(String awal, String akhir){
        String tglawal = awal;
        String tglakhir = akhir;
        if (statnya.equals("Approved")){
            statnya = "Lunas";
        }
        int n = cekJml(awal, akhir);
        for(int i = 0; i<=n; i++){
            try{
                //For MySql
                String sql1 = "INSERT INTO KONDISI values (null ,'"+txtKamar.getText()+"' , '" + tglawal + "','Booked')";
                c.script.executeUpdate(sql1);
                tglawal = ubahTgl(tglawal);
            }catch(SQLException e){
                System.err.print(e);
            }
        }
        try{
                //For MySql
                String sql1 = "INSERT INTO SEWA values (null, '"+txtKamar.getText()+"' , '" + awal + "', '" + akhir + "', '" + session.getUsername() + "', '"+cekHarga()+"', '"+statnya+"')";
                c.script.executeUpdate(sql1);
                JOptionPane.showMessageDialog(this, "Data Berhasil Diubah!");
        }
        
        catch(SQLException e){
                System.err.print(e);
                JOptionPane.showMessageDialog(this, "Terjadi Kesalahan");
        }
    }
    
    public void batalBooking(String awal, String akhir){
        String tglawal = awal;
        String tglakhir = akhir;
        int n = cekJml(awal, akhir);
        for(int i = 0; i<=n; i++){
            try{
                //For MySql
                String sql1 = "DELETE FROM kondisi where id_kamar = '"+txtKamar.getText()+"' AND tgl ='"+tglawal+"'";
                c.script.executeUpdate(sql1);
                tglawal = ubahTgl(tglawal);
            }catch(SQLException e){
                System.err.print(e);
            }
        }
        try{
                //For MySql
                String sql1 = "DELETE FROM sewa where id_kamar =  '"+txtKamar.getText()+"' AND tgl_masuk ='"+awal+"'";
                c.script.executeUpdate(sql1);
        }
        
        catch(SQLException e){
                System.err.print(e);
                JOptionPane.showMessageDialog(this, "Terjadi Kesalahan");
        }
    }
    
    public void cekInKamar(){
        String tglawal = tanggalnyaMasuk;
        String tglakhir = tanggalnyaKeluar;
        int n = cekJml(tglawal, tglakhir);
        for(int i = 0; i<=n; i++){
            try{
                //For MySql
                String sql1 = "UPDATE kondisi SET status = 'CekIn' where id_kamar='"+txtKamar.getText()+"' AND tgl='" +tglawal+"'";
                c.script.executeUpdate(sql1);
                tglawal = ubahTgl(tglawal);
            }catch(SQLException e){
                System.err.print(e);
            }
        }
        try{
                //For MySql
                String sql1 = "UPDATE sewa SET status = 'CekIn' where id_kamar='"+txtKamar.getText()+"' AND tgl_masuk='" +tanggalnyaMasuk+"'";
                c.script.executeUpdate(sql1);
                JOptionPane.showMessageDialog(this, "Cek In Berhasil!");
        }
        
        catch(SQLException e){
                System.err.print(e);
                JOptionPane.showMessageDialog(this, "Terjadi Kesalahan");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        kamar1 = new squareset.SquareSet();
        kamar2 = new squareset.SquareSet();
        kamar3 = new squareset.SquareSet();
        kamar4 = new squareset.SquareSet();
        kamar5 = new squareset.SquareSet();
        kamar6 = new squareset.SquareSet();
        kamar7 = new squareset.SquareSet();
        kamar8 = new squareset.SquareSet();
        kamar9 = new squareset.SquareSet();
        kamar10 = new squareset.SquareSet();
        kamar11 = new squareset.SquareSet();
        kamar12 = new squareset.SquareSet();
        kamar13 = new squareset.SquareSet();
        kamar14 = new squareset.SquareSet();
        kamar15 = new squareset.SquareSet();
        label1 = new java.awt.Label();
        btnSearch = new javax.swing.JButton();
        txtTanggal1 = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tabelSewa = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtKamar = new javax.swing.JLabel();
        txtPenyewa = new javax.swing.JLabel();
        msk = new com.toedter.calendar.JDateChooser();
        klr = new com.toedter.calendar.JDateChooser();
        txtBiaya = new javax.swing.JLabel();
        btEdit = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        btLunas = new javax.swing.JButton();
        btApp = new javax.swing.JButton();
        btCI = new javax.swing.JButton();
        btCO = new javax.swing.JButton();
        txtStatus2 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtNama = new javax.swing.JLabel();
        saldo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        kamar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar1MouseClicked(evt);
            }
        });

        kamar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar2MouseClicked(evt);
            }
        });

        kamar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar3MouseClicked(evt);
            }
        });

        kamar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar4MouseClicked(evt);
            }
        });

        kamar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar5MouseClicked(evt);
            }
        });

        kamar6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar6MouseClicked(evt);
            }
        });

        kamar7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar7MouseClicked(evt);
            }
        });

        kamar8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar8MouseClicked(evt);
            }
        });

        kamar9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar9MouseClicked(evt);
            }
        });

        kamar10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar10MouseClicked(evt);
            }
        });

        kamar11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar11MouseClicked(evt);
            }
        });

        kamar12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar12MouseClicked(evt);
            }
        });

        kamar13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar13MouseClicked(evt);
            }
        });

        kamar14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar14MouseClicked(evt);
            }
        });

        kamar15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kamar15MouseClicked(evt);
            }
        });

        label1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        label1.setText("Daftar Kamar");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(kamar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(kamar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(kamar11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kamar15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSearch))
                            .addComponent(txtTanggal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(txtTanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kamar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kamar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kamar11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kamar15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("List Kamar", jPanel2);

        tabelSewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tabelSewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSewaMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tabelSewa);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("History Penginapan");

        jLabel3.setText("ID Kamar");

        jLabel4.setText("Penyewa");

        jLabel5.setText("Tgl Masuk");

        jLabel6.setText("Tgl Keluar");

        jLabel7.setText("Biaya");

        txtKamar.setText("-");

        txtPenyewa.setText("-");

        txtBiaya.setText("-");

        btEdit.setText("Edit");
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        btBatal.setText("Batalkan");
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });

        btLunas.setText("Lunasi");
        btLunas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLunasActionPerformed(evt);
            }
        });

        btApp.setText("Approve");
        btApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAppActionPerformed(evt);
            }
        });

        btCI.setText("CekIn");
        btCI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCIActionPerformed(evt);
            }
        });

        btCO.setText("CekOut");
        btCO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCOActionPerformed(evt);
            }
        });

        txtStatus2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtStatus2.setText("-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane12)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(msk, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(klr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPenyewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtKamar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBiaya, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btLunas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(txtStatus2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(203, 203, 203))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtKamar)
                    .addComponent(btEdit)
                    .addComponent(btCO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPenyewa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(msk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btBatal)
                        .addComponent(btCI)
                        .addComponent(txtStatus2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(klr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBiaya)
                    .addComponent(btLunas)
                    .addComponent(btApp))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Daftar Inap", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jLabel1.setText("Selamat Datang , ");

        txtNama.setText("-");

        saldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        saldo.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogout)
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogout)
                    .addComponent(jLabel1)
                    .addComponent(txtNama)
                    .addComponent(saldo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kamar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar1MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar1.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar1MouseClicked

    private void kamar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar2MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar2.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar2MouseClicked

    private void kamar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar3MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar3.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar3MouseClicked

    private void kamar4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar4MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar4.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar4MouseClicked

    private void kamar5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar5MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar5.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar5MouseClicked

    private void kamar6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar6MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar6.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar6MouseClicked

    private void kamar7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar7MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar7.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar7MouseClicked

    private void kamar8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar8MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar8.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar8MouseClicked

    private void kamar9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar9MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar9.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar9MouseClicked

    private void kamar10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar10MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar10.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar10MouseClicked

    private void kamar11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar11MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar11.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar11MouseClicked

    private void kamar12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar12MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar12.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar12MouseClicked

    private void kamar13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar13MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar13.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar13MouseClicked

    private void kamar14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar14MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar14.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar14MouseClicked

    private void kamar15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kamar15MouseClicked
        DetailKamar dK = new DetailKamar(tfKamar(kamar15.getString1()),session);
        dK.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kamar15MouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        tanggal1 = String.valueOf(date.format(txtTanggal1.getDate()));
        loadKamar(tanggal1);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        LoginForm lF = new LoginForm();
        lF.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void tabelSewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSewaMouseClicked
        if(evt.getClickCount()==2){
         showData(this.tdSewa.get(tabelSewa.getSelectedRow()));
         tanggalnyaMasuk = this.tdSewa.get(tabelSewa.getSelectedRow()).getTgl_masuk();
         tanggalnyaKeluar = this.tdSewa.get(tabelSewa.getSelectedRow()).getTgl_keluar();
         hargatot = this.tdSewa.get(tabelSewa.getSelectedRow()).getHargatotal();
         statnya = this.tdSewa.get(tabelSewa.getSelectedRow()).getStatus();
         if (session.getRole().equals("Member")){
            viewMember();
            btEdit.setEnabled(true);
            btBatal.setEnabled(true);
            if(this.tdSewa.get(tabelSewa.getSelectedRow()).getStatus().equals("Booked")){
            btLunas.setEnabled(true);
            }
         }
         else {
            viewAdmin();
            if(this.tdSewa.get(tabelSewa.getSelectedRow()).getStatus().equals("CekIn")){
                btCO.setEnabled(true);
            }
            if(this.tdSewa.get(tabelSewa.getSelectedRow()).getStatus().equals("Approved")){
                btCI.setEnabled(true);
            }
            if(this.tdSewa.get(tabelSewa.getSelectedRow()).getStatus().equals("Lunas")){
                btApp.setEnabled(true);
            }
         }
    }
    }//GEN-LAST:event_tabelSewaMouseClicked

    private void btBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBatalActionPerformed
        batalBooking(tanggalnyaMasuk,tanggalnyaKeluar);
        Tampil();
        loadKamar(tanggal1);
        JOptionPane.showMessageDialog(this, "Booking berhasil dibatalkan!");
    }//GEN-LAST:event_btBatalActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        Boolean cek;
        String ttgmsk = String.valueOf(date.format(msk.getDate()));
        String ttgklr = String.valueOf(date.format(klr.getDate()));
        cek = cekKetersediaan(ttgmsk,ttgklr);
        if (cek){
            JOptionPane.showMessageDialog(this, "Kamar "+txtKamar.getText()+" pada Tanggal Tersebut Sudah di Tempati");
        }
        else {
            int jawab = JOptionPane.showConfirmDialog(this, "Yakin Sudah Benar?");
    
            switch(jawab){
                case JOptionPane.YES_OPTION: 
                    batalBooking(tanggalnyaMasuk,tanggalnyaKeluar);
                    insertBooking(ttgmsk,ttgklr);
                    Tampil();
                    loadKamar(tanggal1);
                break;
                case JOptionPane.NO_OPTION:
                break;
        }
        }
    }//GEN-LAST:event_btEditActionPerformed

    private void btLunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLunasActionPerformed
        if (hargatot>session.getSaldo()){
            JOptionPane.showMessageDialog(this, "Saldo Tidak Mencukupi!");
        }
        else{
        lunasin();
        }
    }//GEN-LAST:event_btLunasActionPerformed

    private void btAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAppActionPerformed
    try{
            String sql = "UPDATE sewa SET status = 'Approved' where id_kamar='"+txtKamar.getText()+"' AND tgl_masuk ='"+tanggalnyaMasuk+"'";
            c.script.executeUpdate(sql);
            txtStatus2.setText("Approved");
            Tampil();
            
            JOptionPane.showMessageDialog(null, "Approval Berhasil");
            }
        	catch(SQLException ex){
        	System.err.print(ex);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
        }
    }//GEN-LAST:event_btAppActionPerformed

    private void btCIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCIActionPerformed
        cekInKamar();
        Tampil();
        txtStatus2.setText("CekIn");
        loadKamar(tanggal1);
    }//GEN-LAST:event_btCIActionPerformed

    private void btCOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCOActionPerformed
    try{
            String sql = "UPDATE sewa SET status = 'CekOut' where id_kamar='"+txtKamar.getText()+"' AND tgl_masuk ='"+tanggalnyaMasuk+"'";
            c.script.executeUpdate(sql);
            txtStatus2.setText("CekOutt");
            Tampil();
            
            JOptionPane.showMessageDialog(null, "CekOut Berhasil");
            }
        	catch(SQLException ex){
        	System.err.print(ex);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
        }
    }//GEN-LAST:event_btCOActionPerformed

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
            java.util.logging.Logger.getLogger(ListKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListKamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListKamar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btApp;
    private javax.swing.JButton btBatal;
    private javax.swing.JButton btCI;
    private javax.swing.JButton btCO;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btLunas;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JTabbedPane jTabbedPane1;
    private squareset.SquareSet kamar1;
    private squareset.SquareSet kamar10;
    private squareset.SquareSet kamar11;
    private squareset.SquareSet kamar12;
    private squareset.SquareSet kamar13;
    private squareset.SquareSet kamar14;
    private squareset.SquareSet kamar15;
    private squareset.SquareSet kamar2;
    private squareset.SquareSet kamar3;
    private squareset.SquareSet kamar4;
    private squareset.SquareSet kamar5;
    private squareset.SquareSet kamar6;
    private squareset.SquareSet kamar7;
    private squareset.SquareSet kamar8;
    private squareset.SquareSet kamar9;
    private com.toedter.calendar.JDateChooser klr;
    private java.awt.Label label1;
    private com.toedter.calendar.JDateChooser msk;
    private javax.swing.JLabel saldo;
    private javax.swing.JTable tabelSewa;
    private javax.swing.JLabel txtBiaya;
    private javax.swing.JLabel txtKamar;
    private javax.swing.JLabel txtNama;
    private javax.swing.JLabel txtPenyewa;
    private javax.swing.JLabel txtStatus2;
    private com.toedter.calendar.JDateChooser txtTanggal1;
    // End of variables declaration//GEN-END:variables
}

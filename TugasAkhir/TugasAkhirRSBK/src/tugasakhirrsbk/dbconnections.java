/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugasakhirrsbk;

/**
 *
 * @author Rayc
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class dbconnections {
    //deklarasi variabel
    Connection c;
    Statement script;
    
    public dbconnections(){
        try{            
//sebagai class reference untuk conect ke database MySQL
Class.forName("com.mysql.jdbc.Driver");
c = DriverManager.getConnection("jdbc:mysql://localhost:3306/modul6_kel32","root","");
script = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //memberikan peringatan bila bila koneksi ke database berhasil
            System.out.println("Koneksi Sukses");
        }catch(SQLException | ClassNotFoundException ex){
            System.err.print(ex);
        }
    }
}


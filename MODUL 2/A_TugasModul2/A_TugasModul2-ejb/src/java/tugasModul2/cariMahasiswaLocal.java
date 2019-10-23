/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugasModul2;

import javax.ejb.Local;

/**
 *
 * @author Alfian
 */
@Local
public interface cariMahasiswaLocal {
    String search(String param);
    void cekpicek(String nama,String nim);
    
}

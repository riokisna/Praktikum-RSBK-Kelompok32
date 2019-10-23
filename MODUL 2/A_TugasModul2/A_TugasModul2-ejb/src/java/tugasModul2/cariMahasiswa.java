/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugasModul2;

import javax.ejb.Stateful;
import java.util.ArrayList;

@Stateful

public class cariMahasiswa implements cariMahasiswaLocal {
    ArrayList<Mahasiswa> datar = new ArrayList<Mahasiswa>();
    public void cekpicek(String nama,String nim){
        datar.add(new Mahasiswa(nama,nim));
    }
    
    public cariMahasiswa() {
          cekpicek("Rio Kisna Eka Putra","21120116130060");
          cekpicek("Alfian Aulia Firdaus","21120116130035");
          cekpicek("Favo Perdana HS","21120116120015");
          cekpicek("Farrell Denando","21120116120026");
          cekpicek("Faisal Rizki R","21120116120014");
    }

    private String checkNama(String param){
    for (int i = 0; i < datar.size(); i++){
        if(param.equals(datar.get(i).nama)){
            return "Nama Praktikan : " +datar.get(i).nama+"("+datar.get(i).nim+")";
        }
    }
    return null;
}
private String checkNIM(String param){
    for (int i = 0; i < datar.size(); i++) {
        if(param.equals(datar.get(i).nim)){
            return "Nama Praktikan : " +datar.get(i).nama+"("+datar.get(i).nim+")";
        }
    }
    return null;
}

public String search(String param){
    if (checkNama(param) != null) {
        return checkNama(param);
    }
    else if (checkNIM(param) !=null) {
        return checkNIM(param);
    }
    else {
        return "Nope";
    }
};
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

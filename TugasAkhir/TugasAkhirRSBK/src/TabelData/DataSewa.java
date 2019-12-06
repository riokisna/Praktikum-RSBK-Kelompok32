/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabelData;

/**
 *
 * @author Rayc
 */
public class DataSewa {
    String id_kamar, tgl_masuk, tgl_keluar, username, status;
    int hargatotal;

    public String getId_kamar() {
        return id_kamar;
    }

    public void setId_kamar(String id_kamar) {
        this.id_kamar = id_kamar;
    }

    public String getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(String tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }

    public String getTgl_keluar() {
        return tgl_keluar;
    }

    public void setTgl_keluar(String tgl_keluar) {
        this.tgl_keluar = tgl_keluar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHargatotal() {
        return hargatotal;
    }

    public void setHargatotal(int hargatotal) {
        this.hargatotal = hargatotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}

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
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TabelDataKamar extends AbstractTableModel{
    List <DataKamar> data = new ArrayList<>();
    

    @Override
    public int getRowCount() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return data.size();
    }

    @Override
    public int getColumnCount() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        switch(columnIndex){
        case 0: return data.get(rowIndex).getId_kamar();
        case 1: return data.get(rowIndex).getKelas();
        case 2: return data.get(rowIndex).getHarga();
        case 3: return data.get(rowIndex).getFasilitas();
        case 4: return data.get(rowIndex).getStatus();
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int kolom){
    switch(kolom){
        case 0: return"ID_KAMAR";
        case 1: return"KELAS";
        case 2: return"HARGA";
        case 3: return"FASILITAS";
        case 4: return"STATUS";
        default: return null;
    	}
    }
    
    public void add(DataKamar a){
        data.add(a);
        fireTableRowsInserted(getRowCount(),getColumnCount());
    }

    public void delete (int col,int row){
        data.remove(col);
        fireTableRowsDeleted(col,row);
    }
    
  

    public DataKamar get(int row){
        return (DataKamar) data.get(row);
    }

}
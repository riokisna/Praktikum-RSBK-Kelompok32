/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabelData;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rayc
 */
public class TabelDataSewa extends AbstractTableModel {
    List <DataSewa> data = new ArrayList<>();
    
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
        case 1: return data.get(rowIndex).getTgl_masuk();
        case 2: return data.get(rowIndex).getTgl_keluar();
        case 3: return data.get(rowIndex).getUsername();    
        case 4: return data.get(rowIndex).getHargatotal();
        case 5: return data.get(rowIndex).getStatus(); 
            default: return null;
        }
    }
@Override
    public String getColumnName(int kolom){
    switch(kolom){
        case 0: return"ID_KAMAR";
        case 1: return"TGL_MASUK";
        case 2: return"TGL_KELUAR";
        case 3: return"USERNAME";
        case 4: return"HARGATOTAL";
        case 5: return"STATUS";
        default: return null;
    	}
    }
    
    public void add(DataSewa a){
        data.add(a);
        fireTableRowsInserted(getRowCount(),getColumnCount());
    }

    public void delete (int col,int row){
        data.remove(col);
        System.out.println("ANCJE");
        fireTableRowsDeleted(col,row);
    }

    public DataSewa get(int row){
        return (DataSewa) data.get(row);
    }
    
}

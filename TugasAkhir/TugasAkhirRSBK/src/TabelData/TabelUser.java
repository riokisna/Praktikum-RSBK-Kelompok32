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
public class TabelUser extends AbstractTableModel{
    List <DataUser> data = new ArrayList<>();
    

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
        case 0: return data.get(rowIndex).getUsername();
        case 1: return data.get(rowIndex).getPassword();
        case 2: return data.get(rowIndex).getNama();
        case 3: return data.get(rowIndex).getNik();
        case 4: return data.get(rowIndex).getAsal();
        case 5: return data.get(rowIndex).getSaldo();
        case 6: return data.get(rowIndex).getRole();
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int kolom){
    switch(kolom){
        case 0: return"USERNAME";
        case 1: return"PASSWORD";
        case 2: return"NIK";
        case 3: return"NAMA";
        case 4: return"ASAL";
        case 5: return"SALDO";
        case 6: return"ROLE";
        default: return null;
    	}
    }
    
    public void add(DataUser a){
        data.add(a);
        fireTableRowsInserted(getRowCount(),getColumnCount());
    }

    public void delete (int col,int row){
        data.remove(col);
        fireTableRowsDeleted(col,row);
    }
    
  

    public DataUser get(int row){
        return (DataUser) data.get(row);
    }

}
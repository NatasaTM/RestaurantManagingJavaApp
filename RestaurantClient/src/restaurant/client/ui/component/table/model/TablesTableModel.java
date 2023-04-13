package restaurant.client.ui.component.table.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.Table;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class TablesTableModel extends AbstractTableModel{
    private final List<Table> tables;

    public TablesTableModel(List<Table> tables) {
        this.tables = tables;
    }
    
    

    @Override
    public int getRowCount() {
        if(tables==null){
            return 0;
        }
        return tables.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         Table table = tables.get(rowIndex);
         if(columnIndex==0) return table.getTableNumber();
         if(columnIndex==1) return table.getNumberOfSeats();
         if(columnIndex==2) return table.isIsAvailable();
         
         return"n/a";
    }

    @Override
    public String getColumnName(int column) {
         if(column==0) return"Broj stola";
         if(column==1) return"Broj mesta za sedenje";
         if(column==2) return"Slobodan";
         
         return"n/a";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 2) return Boolean.class;
        else return Object.class;
    }
     
    
    
}

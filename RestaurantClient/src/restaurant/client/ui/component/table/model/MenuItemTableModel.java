package restaurant.client.ui.component.table.model;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.MenuItem;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuItemTableModel extends AbstractTableModel {

    private final List<MenuItem> menuItems;

    public MenuItemTableModel(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public int getRowCount() {

        if (menuItems == null) {
            return 0;
        }
        return menuItems.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MenuItem menuItem = menuItems.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return menuItem.getId();
            case 1:
                return menuItem.getName();
            case 2:
                return menuItem.getDescription();
            case 3:
                DecimalFormat df = new DecimalFormat("0.00");
        
                return df.format(menuItem.getPrice());
            case 4:
                return menuItem.getMenuCategory();
                
            case 5:
                return menuItem.getMenuItemType();

            default:
                break;

        }
        return "n/a";
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "NAZIV";
            case 2:
                return "OPIS";
            case 3:
                return "CENA";
            case 4:
                return "KATEGORIJA";
            case 5:
                return "TIP";

            default:
                break;

        }
        return "n/a";
    }
    
    

}

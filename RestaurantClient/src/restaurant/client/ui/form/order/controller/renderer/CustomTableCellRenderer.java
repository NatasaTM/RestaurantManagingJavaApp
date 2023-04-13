package restaurant.client.ui.form.order.controller.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class CustomTableCellRenderer extends DefaultTableCellRenderer{
    private final Color selectedColor = Color.YELLOW;
    private final int selectedRowIndex;
    private boolean isRowDeleted = false;

    public void setIsRowDeleted(boolean isRowDeleted) {
        this.isRowDeleted = isRowDeleted;
    }
    
    

    public CustomTableCellRenderer(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(selectedRowIndex!=-1){
           if (isSelected || row == selectedRowIndex) {
            c.setBackground(selectedColor);
        } else {
           // c.setBackground(table.getBackground());
           if (!isRowDeleted) {
                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());
            }
        }
        
        return c;  
        }
       return null;
    }
}

package restaurant.client.ui.component.table.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.User;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class AccountTableModel extends AbstractTableModel{
    
    private final List<User> users;

    public AccountTableModel(List<User> users) {
        this.users = users;
    }
    

    @Override
    public int getRowCount() {
       if(users==null){
           return 0;
       }
       return users.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        
        if(columnIndex==0) return user.getUsername();
        if(columnIndex==1) return user.getPassword();
        if(columnIndex==2) return user.getEmployee().getId();
        if(columnIndex==3) return user.getEmployee().getFirstname();
        if(columnIndex==4) return user.getEmployee().getLastname();
        if(columnIndex==5) return user.getRoles();
        
        return null;
    }

    @Override
    public String getColumnName(int column) {
        if(column==0) return "Korisnicko ime";
        if(column==1) return "Lozinka";
        if(column==2) return "ID zaposlenog";
        if(column==3) return "Ime";
        if(column==4) return "Prezime";
        if(column==5) return "Radno mesto";
        return null;
    }
    
    
    
}

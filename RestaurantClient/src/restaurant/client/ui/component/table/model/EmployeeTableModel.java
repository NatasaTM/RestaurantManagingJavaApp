package restaurant.client.ui.component.table.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.Employee;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class EmployeeTableModel extends AbstractTableModel {
    private final List<Employee> employees;

    public EmployeeTableModel(List<Employee> employees) {
        this.employees = employees;
    }
    
    

    @Override
    public int getRowCount() {
       if(employees==null){
           return 0;
       }
       return employees.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         Employee employee = employees.get(rowIndex);
         if(columnIndex==0) return employee.getId();
         if(columnIndex==1) return employee.getJmbg();
         if(columnIndex==2) return employee.getFirstname();
         if(columnIndex==3) return employee.getLastname();
         if(columnIndex==4) return employee.getBirthdate();
         if(columnIndex==5) return employee.getAdress();
         if(columnIndex==6) return employee.getCity().getName();
         
         return "n/a";
    }

    @Override
    public String getColumnName(int column) {
       if(column==0) return"Id";
       if(column==1) return"JMBG";
       if(column==2) return"Ime";
       if(column==3) return"Prezime";
       if(column==4) return"Datum rodjenja";
       if(column==5) return"Adresa";
       if(column==6) return"Grad";
       
       return "n/a";
    }
    
    
}

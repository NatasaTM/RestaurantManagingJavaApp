package restaurant.server.controller;

import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.server.service.EmployeeService;
import restaurant.server.service.impl.EmployeeServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class EmployeeController {
    
    private final static EmployeeController instance = new EmployeeController();
    
    private EmployeeService employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeServiceImpl();
    }

    public static EmployeeController getInstance() {
        return instance;
    }
    
    public List<Employee> getAllEmployees() throws Exception{
        return employeeService.getAll();
    }
    
    public Employee findEmployeeById(Integer id) throws Exception{
        return employeeService.findById(id);
    }
    
    public void addEmployee(Employee employee) throws Exception{
        employeeService.add(employee);
    }
    
    public void updateEmployee(Employee employee) throws Exception{
        employeeService.update(employee);
    }
    
    public void deleteEmployee(Employee employee) throws Exception{
        employeeService.delete(employee);
    }
    
}

package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.Employee;


/**
 *
 * @author Natasa Todorov Markovic
 */
public interface EmployeeService {
    
    List<Employee> getAll() throws Exception;
    void add(Employee employee) throws Exception;
    void delete(Employee employee) throws Exception;
    void update(Employee employee) throws Exception;
    Employee findById(Integer id) throws Exception;
    
}

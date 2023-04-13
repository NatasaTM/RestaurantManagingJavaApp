package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.EmployeeRepositoryImpl;
import restaurant.server.service.EmployeeService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class EmployeeServiceImpl implements EmployeeService {

    private final GenericRepository<Employee,Integer> employeeRepositoryImpl;

    public EmployeeServiceImpl() {
       employeeRepositoryImpl = new EmployeeRepositoryImpl();

    }

    @Override
    public List<Employee> getAll() throws Exception {
        return employeeRepositoryImpl.getAll();
    }

    @Override
    public void add(Employee employee) throws Exception {
        String query = "SELECT e.employeeId,e.jmbg,e.firstname,e.lastname,e.birthdate,e.adress,c.zipcode,c.name cityname FROM employee e\n"
                + "JOIN city c ON e.cityZipcode=c.zipcode\n"
                + "WHERE jmbg='" + employee.getJmbg() + "'";
        
        List<Employee> employees = employeeRepositoryImpl.findByQuery(query);
        if(employees.isEmpty()){
            employeeRepositoryImpl.add(employee);
        }else
            throw new Exception("Zaposleni vec postoji u bazi");
    }

    @Override
    public void delete(Employee employee) throws Exception {
        employeeRepositoryImpl.delete(employee);
    }

    @Override
    public void update(Employee employee) throws Exception {
         employeeRepositoryImpl.update(employee);
    }

    @Override
    public Employee findById(Integer id) throws Exception {
        return employeeRepositoryImpl.findById(id);
    
    
    
    }

}

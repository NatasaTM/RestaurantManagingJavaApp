package restaurant.common.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Employee implements Serializable{
    
    private Integer id;
    private String jmbg;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String adress;
    private City city;

    public Employee() {
    }

    public Employee(Integer id, String jmbg, String firstname, String lastname, LocalDate birthdate, String adress, City city) {
        this.id = id;
        this.jmbg = jmbg;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.adress = adress;
        this.city = city;
    }

    public Employee(Integer id) {
        this.id = id;
    }
    
    

    

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", " + firstname + " " + lastname;
    }

    
  

    

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        return Objects.equals(this.jmbg, other.jmbg);
    }
    
    

    

    
    
    
    
}

package restaurant.common.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class City implements Serializable{
    
    private Long zipcode;
    private String name;

    public City() {
    }

    public City(Long zipcode, String name) {
        this.zipcode = zipcode;
        this.name = name;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return zipcode+ " " + name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final City other = (City) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.zipcode, other.zipcode);
    }

    
    
    
    
}

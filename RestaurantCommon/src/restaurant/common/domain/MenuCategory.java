package restaurant.common.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuCategory implements Serializable{
    
    private Integer id;
    private String name;

    public MenuCategory() {
    }

    public MenuCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name ;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final MenuCategory other = (MenuCategory) obj;
        return Objects.equals(this.name, other.name);
    }
    
    
    
}

package restaurant.common.domain;

import java.io.Serializable;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Role implements Serializable{
    
    private Integer id;
    private String name;

    public Role() {
    }

    public Role(Integer id, String name) {
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
        return "id=" + id + ", Naziv: " + name;
    }
    
    
    
}

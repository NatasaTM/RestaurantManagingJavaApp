package restaurant.common.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Menu implements Serializable{
    
    private Integer id;
    private String name;
    private LocalDate date;
    private List<MenuItem> menuItems;
    private boolean isActiv;

    public Menu() {
        menuItems = new ArrayList<>();
       
    }

    public Menu(Integer id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
    

    public Menu(Integer id, String name, LocalDate date, List<MenuItem> menuItems) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.menuItems = menuItems;
    }

    public Menu(Integer id, String name, LocalDate date,boolean isActive) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.isActiv=isActive;
        menuItems = new ArrayList<>();
    }

    public Menu(String name) {
        this.name = name;
        this.date=LocalDate.now();
        menuItems = new ArrayList<>();
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return id + " " + name + " " + date.format(df);
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
        final Menu other = (Menu) obj;
        return Objects.equals(this.name, other.name);
    }

    public boolean isIsActiv() {
        return isActiv;
    }

    public void setIsActiv(boolean isActiv) {
        this.isActiv = isActiv;
    }
    
    
    
    
    
    
    
    
}

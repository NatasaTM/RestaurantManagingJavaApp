package restaurant.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuItem implements Serializable{
    
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private MenuCategory menuCategory;
    private MenuItemType menuItemType;

    public MenuItem() {
    }

    public MenuItem(Integer id, String name, String description, BigDecimal price, MenuCategory menuCategory,MenuItemType menuItemType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        price.setScale(2, RoundingMode.HALF_UP);
        this.menuCategory = menuCategory;
        this.menuItemType = menuItemType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MenuCategory getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
    }

    @Override
    public String toString() {
        return "id:" + id + ", Naziv: " + name + ", Opis: " + description + ", Cena: " + price + " rsd , Kategorija: " + menuCategory ;
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
        final MenuItem other = (MenuItem) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.price, other.price);
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public void setMenuItemType(MenuItemType menuItemType) {
        this.menuItemType = menuItemType;
    }

   
    
    

    

    
    
    

   
    
    
}

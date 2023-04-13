package restaurant.common.domain;

import java.io.Serializable;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Table implements Serializable{

    private Integer tableNumber;
    private Integer numberOfSeats;
    private boolean isAvailable;

    public Table() {
    }

    public Table(Integer tableNumber, Integer numberOfSeats, boolean isAvailable) {
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        this.isAvailable = isAvailable;
    }

    public Table(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
         this.isAvailable = true;
    }
    
    

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public String toString() {
        return "Broj stola: " + tableNumber + ", Broj mesta za sedenje: " + numberOfSeats ;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Object getSelectionModel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

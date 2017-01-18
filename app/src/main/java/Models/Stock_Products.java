package Models;


import java.io.Serializable;

/**
 * Created by orobe on 27/12/2016.
 */

public class Stock_Products extends Products implements Serializable {

    private int quantity;

    public  Stock_Products(){}

    public Stock_Products(String name, int quantity) {
        super(name);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

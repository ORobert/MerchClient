package Models;


import java.io.Serializable;

/**
 * Created by orobe on 27/12/2016.
 */


public class Shop_Products extends Products implements Serializable {

    private double price;

    public Shop_Products() {

    }

    public Shop_Products(String name, double price) {
        super(name);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

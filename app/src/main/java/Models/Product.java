package Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by orobe on 27/12/2016.
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private int quantity;
	private double price;
//    private List<Order> orders;

    public Product(){}

//
//    public List<Order> getOrderList() {
//        return orders;
//    }
//
//    public void setOrderList(List<Order> orders) {
//        this.orders = orders;
//    }

    public Product(final String name){
        this.name=name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        String stock="";
        if(this.quantity > 0){
            stock="In Stock";
        }
        else {
            stock="Stock 0";
        }
        return name + "------" + price + "   " + stock;
    }
}

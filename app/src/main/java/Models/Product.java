package Models;

import java.io.Serializable;

/**
 * Created by orobe on 27/12/2016.
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private int quantity;
	private double price;

    public Product(){}

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
        return "Name "+name;
    }
}

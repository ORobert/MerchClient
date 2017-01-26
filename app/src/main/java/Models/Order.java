package Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergiu on 19-Jan-17.
 */
public class Order implements Serializable{
	private int id;
	private String state;
	//private User owner;
	private String username;
	private Double latitude;
	private Double longitude;
	private Date date;
	private int prodCount;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getProdCount() {
		return prodCount;
	}

	public void setProdCount(int prodCount) {
		this.prodCount = prodCount;
	}
	//private List<Product> products;
	//private User driver;

	public Order(){
		state="Confirmed";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}*/

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/*public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}*/
}

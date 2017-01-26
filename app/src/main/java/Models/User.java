package Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sergiu on 19-Jan-17.
 */
public class User implements Serializable{
	private int id;
	private String username;
	private String password;
	private UserType userType;

	//private List<Order> orders;

	/*public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
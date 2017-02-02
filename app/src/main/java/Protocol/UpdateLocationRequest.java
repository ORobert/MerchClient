package Protocol;

import Models.Order;
import Protocol.Request;

import java.util.List;

/**
 * Created by Sergiu on 29-Jan-17.
 */
public class UpdateLocationRequest implements UpdateRequest {
	private List<Order> orders;
	private double longitude, latitude;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}

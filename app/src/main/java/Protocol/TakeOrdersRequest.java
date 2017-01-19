package Protocol;

import Models.Order;

import java.util.List;

/**
 * Created by Sergiu on 19-Jan-17.
 */
public class TakeOrdersRequest {
	private List<Order> orders;

	public TakeOrdersRequest(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}

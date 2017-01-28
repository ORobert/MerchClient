package Protocol;

import Models.Order;

import java.util.List;

/**
 * Created by Sergiu on 25-Jan-17.
 */
public class GetOrdersResponse implements Response {
	private List<Order> orders;

	public GetOrdersResponse(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}

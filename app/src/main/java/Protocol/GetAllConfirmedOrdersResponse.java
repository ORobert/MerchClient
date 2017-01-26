package Protocol;

import Models.Order;

import java.util.List;

/**
 * Created by Sergiu on 25-Jan-17.
 */
public class GetAllConfirmedOrdersResponse implements Response {
	private List<Order> orders;

	public GetAllConfirmedOrdersResponse(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}

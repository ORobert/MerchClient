package Protocol;

import Models.Order;
import Models.User;

import java.util.List;

/**
 * Created by Sergiu on 19-Jan-17.
 */
public class TakeOrdersRequest implements Request {
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

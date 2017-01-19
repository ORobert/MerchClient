package Protocol;

import Models.Order;

/**
 * Created by Sergiu on 19-Jan-17.
 */
public class OrderProductsRequest implements Request {
	private Order order;

	public OrderProductsRequest(Order order) {
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}

package Protocol;

import Models.Order;

/**
 * Created by Sergiu on 03-Feb-17.
 */
public class GetLocationRequest implements Request {
	private Order order;

	public GetLocationRequest(Order order) {
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}

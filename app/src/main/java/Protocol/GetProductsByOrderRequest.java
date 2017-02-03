package Protocol;

import Models.Order;

/**
 * Created by Sergiu on 02-Feb-17.
 */
	public class GetProductsByOrderRequest implements Request {
		private Order order;

		public GetProductsByOrderRequest(Order order) {
			this.order = order;
		}

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}
	}

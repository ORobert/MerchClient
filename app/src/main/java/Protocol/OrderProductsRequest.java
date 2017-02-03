package Protocol;

import Models.Order;
import Models.Product;

import java.util.List;

/**
 * Created by Sergiu on 19-Jan-17.
 */
public class OrderProductsRequest implements Request {
	private Integer ownerId;
	private List<Product> products;


	public OrderProductsRequest(Integer ownerId, List<Product> products) {
		this.ownerId = ownerId;
		this.products = products;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
}

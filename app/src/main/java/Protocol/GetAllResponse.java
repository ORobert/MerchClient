package Protocol;

import Models.Product;

import java.util.List;


public class GetAllResponse implements Response {
    private List<Product> products;

    public GetAllResponse(List<Product> products) {
        this.products = products;
    }

	public GetAllResponse() {
	}

	public List<Product> getProductsList() {
        return products;
    }

    public void setProductsList(List<Product> productList) {
        this.products = productList;
    }
}
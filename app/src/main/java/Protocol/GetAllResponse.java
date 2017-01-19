package Protocol;

import Models.Product;

import java.util.List;


public class GetAllResponse implements Response {
    List<Product> products;

    public List<Product> getProductsList() {
        return products;
    }

    public void setProductsList(List<Product> productList) {
        this.products = productList;
    }
}
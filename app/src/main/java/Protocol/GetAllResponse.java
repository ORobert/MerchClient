package Protocol;

import Models.Products;

import java.util.List;


public class GetAllResponse implements Response{
    List<Products> products;

    public List<Products> getProductsList() {
        return products;
    }

    public void setProductsList(List<Products> productsList) {
        this.products = productsList;
    }
}

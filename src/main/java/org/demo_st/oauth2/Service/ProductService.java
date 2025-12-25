package org.demo_st.oauth2.Service;

import org.demo_st.oauth2.ENTITY.ProductResponse;
import org.demo_st.oauth2.ENTITY.Products;
import org.demo_st.oauth2.REPO.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private Productrepo repo;

    public List<Products> addProduct(String name, String category, int quantity, int price) {
        Products product = new Products();
        product.setName(name);
        product.setCategory(category);
        product.setQuantity(quantity);
        product.setPrice(price);
        repo.save(product);
        return repo.findAll();
    }

    public List<Products> findAll() {
        return repo.findAll();
    }

    public List<Products> findByCategory(String category) {
        return repo.findByCategory(category);
    }

    public Products updatequantity(Integer id, Integer quantity) {
        Products product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setQuantity(product.getQuantity() + quantity);
        return repo.save(product);
    }

    public Products updatePrice(int id, int price) {
        Products prod = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        prod.setPrice(price);
        return repo.save(prod);
    }


    public ProductResponse deleteProduct(int id) {
        Products products = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        repo.delete(products);
        return new ProductResponse("product deleted successfully" , 200 );
    }
}

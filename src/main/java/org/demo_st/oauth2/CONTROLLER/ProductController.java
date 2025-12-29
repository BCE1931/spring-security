package org.demo_st.oauth2.CONTROLLER;

import org.demo_st.oauth2.ENTITY.ProductResponse;
import org.demo_st.oauth2.ENTITY.Products;
import org.demo_st.oauth2.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @MutationMapping
    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    public List<Products> addProduct (@Argument String name, @Argument String category , @Argument int quantity , @Argument int price){
        return productService.addProduct(name,category,quantity,price);
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('WEATHER_READ')")
    public List<Products> findAll(){
        return productService.findAll();
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    public Products findById(@Argument int id){
        return productService.findById(id);
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('WEATHER_READ')")
    public List<Products> findByCategory(@Argument String category){
        return productService.findByCategory(category);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    public Products updateQuantity(@Argument int id, @Argument int quantity) {
        return productService.updatequantity(id, quantity);
    }


    @MutationMapping
    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    public Products updatePrice(@Argument int id , @Argument int price){
        return productService.updatePrice(id,price);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('WEATHER_DELETE')")
    public ProductResponse deleteProduct(@Argument int id){
        return productService.deleteProduct(id);
    }
}


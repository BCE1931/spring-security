package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Productrepo extends JpaRepository<Products, Integer> {
    List<Products> findByCategory(String category);
}


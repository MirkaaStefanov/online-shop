package com.example.onlineshop.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    List<Product> findProductById(Integer id);

    @Query("SELECT p FROM Product p JOIN p.productType pt WHERE p.name LIKE %:search% OR pt.name LIKE %:search%")
    List<Product> findProductByNameOrCategory(String search);
}

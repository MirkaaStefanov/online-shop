package com.example.onlineshop.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.id = :id OR p.price = :id AND p.quantity > 0")
    List<Product> findProductById(Integer id);

    @Query("SELECT p FROM Product p JOIN p.productType pt WHERE p.name LIKE %:search% OR pt.name LIKE %:search% AND p.quantity > 0 ORDER BY p.expires_in")
    List<Product> findProductByNameOrCategory(String search);
    @Query("Select p FROM Product p WHERE p.quantity > 0 ORDER BY p.expires_in")
    List<Product> findAll();
    @Query("SELECT p FROM Product p " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:productTypeId IS NULL OR p.productType.id = :productTypeId) " +
            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.price BETWEEN :minPrice AND :maxPrice) AND p.quantity > 0 ORDER BY p.expires_in")
    List<Product> filter(String name, Integer productTypeId, double minPrice, double maxPrice);

    @Query("Select p FROM Product p WHERE p.quantity < 1 ORDER BY p.expires_in")
    List <Product> outOfStockProducts();


}

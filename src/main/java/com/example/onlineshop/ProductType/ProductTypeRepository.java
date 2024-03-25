package com.example.onlineshop.ProductType;

import org.springframework.data.repository.CrudRepository;

public interface ProductTypeRepository extends CrudRepository <ProductType, Integer> {
    ProductType findByName(String name);
}

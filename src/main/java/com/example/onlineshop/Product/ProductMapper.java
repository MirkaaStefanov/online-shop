package com.example.onlineshop.Product;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setProductType(productDto.getProductType());
        product.setColor(productDto.getColor());
        product.setPrice(productDto.getPrice());
        product.setExpires_in(productDto.getExpires_in());
        product.setQuantity(productDto.getQuantity());

        return product;
    }
}

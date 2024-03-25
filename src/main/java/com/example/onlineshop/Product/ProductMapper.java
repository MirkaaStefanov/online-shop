package com.example.onlineshop.Product;

import com.example.onlineshop.ProductType.ProductType;
import com.example.onlineshop.ProductType.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;

@Component
public class ProductMapper {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public Product toFoodEntity(ProductDto productDto) {
        Product product = new Product();
        ProductType type = productTypeRepository.findByName("food");
        product.setName(productDto.getName());
        product.setProductType(type);
        product.setPrice(productDto.getPrice());
        product.setExpires_in(productDto.getExpires_in());
        product.setQuantity(productDto.getQuantity());
        String image = Base64.getEncoder().encodeToString(productDto.getImage());
        product.setImage(image);

        return product;
    }
    public Product toDrinkEntity(ProductDto productDto) {
        Product product = new Product();
        ProductType type = productTypeRepository.findByName("drink");
        product.setName(productDto.getName());
        product.setProductType(type);
        product.setPrice(productDto.getPrice());
        product.setExpires_in(productDto.getExpires_in());
        product.setQuantity(productDto.getQuantity());
        String image = Base64.getEncoder().encodeToString(productDto.getImage());
        product.setImage(image);

        return product;
    }
    public Product toSanitaryEntity(ProductDto productDto) {
        Product product = new Product();
        ProductType type = productTypeRepository.findByName("sanitary");
        product.setName(productDto.getName());
        product.setProductType(type);
        product.setColor(productDto.getColor());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        String image = Base64.getEncoder().encodeToString(productDto.getImage());
        product.setImage(image);

        return product;
    }
    public Product toRailingEntity(ProductDto productDto) {
        Product product = new Product();
        ProductType type = productTypeRepository.findByName("railing");
        product.setName(productDto.getName());
        product.setProductType(type);
        product.setColor(productDto.getColor());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        String image = Base64.getEncoder().encodeToString(productDto.getImage());
        product.setImage(image);

        return product;
    }
    public Product toAccessoryEntity(ProductDto productDto) {
        Product product = new Product();
        ProductType type = productTypeRepository.findByName("accessories");
        product.setName(productDto.getName());
        product.setProductType(type);
        product.setColor(productDto.getColor());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        String image = Base64.getEncoder().encodeToString(productDto.getImage());
        product.setImage(image);

        return product;
    }
    public Product toOthersEntity(ProductDto productDto) {
        Product product = new Product();
        ProductType type = productTypeRepository.findByName("others");
        product.setName(productDto.getName());
        product.setProductType(type);
        product.setColor(productDto.getColor());
        product.setPrice(productDto.getPrice());
        product.setExpires_in(productDto.getExpires_in());
        product.setQuantity(productDto.getQuantity());
        String image = Base64.getEncoder().encodeToString(productDto.getImage());
        product.setImage(image);

        return product;
    }
}

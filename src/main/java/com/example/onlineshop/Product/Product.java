package com.example.onlineshop.Product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "products")
public class Product {

    //product_id,name,price,quantity,type,color,expires_in

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    private String name;
    @NotNull
    private double price;
    @Min(0)
    private int quality;
    @NotNull
    private ProductType productType;
    private String color;
    private LocalDate expires_in;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(LocalDate expires_in) {
        this.expires_in = expires_in;
    }

}

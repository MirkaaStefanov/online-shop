package com.example.onlineshop.ShoppingCart;

import com.example.onlineshop.Product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;

import java.util.Objects;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double price;
    private int quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        double price=this.getQuantity()*product.getPrice();
        this.price = price;

        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

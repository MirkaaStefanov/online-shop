package com.example.onlineshop.ShoppingCart;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private double totalPrice;


    private int itemsNumber;


    @OneToMany(cascade = CascadeType.ALL)
    private Collection<CartItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTotalPrice() {
        double sum=0.0;
        for (CartItem item : this.items) {
            sum +=item.getProduct().getPrice();
        }
        return this.totalPrice=sum;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemsNumber() {
        return this.items.size();
    }

    public void setItemsNumber(int itemsNumber) {
        this.itemsNumber = itemsNumber;
    }

    public Collection<CartItem> getItems() {
        return items;
    }

    public void setItems(Collection<CartItem> items) {
        this.items = items;
    }

}

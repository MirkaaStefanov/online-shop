package com.example.onlineshop.ShoppingCart;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AddToCardDto {
    private Integer productId;
    private int quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package com.example.onlineshop.Order;

import com.example.onlineshop.User.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany
    private Collection<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        List<OrderItem> orderItemList =(List<OrderItem>) orderItems;
        double orderPrice = 0;

        for (int i = 0; i < orderItemList.size(); i++) {
           orderPrice += orderItemList.get(i).getPriceEach() * orderItemList.get(i).getQuantity();
        }
        setPrice(orderPrice);

        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

package com.example.onlineshop.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
    List<Order> findAll();

    @Query("SELECT o FROM Order o WHERE o.orderStatus = :status ORDER BY o.orderDate DESC")
    List<Order> sortByStatus(OrderStatus status);

}

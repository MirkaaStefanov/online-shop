package com.example.onlineshop.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.orderStatus NOT LIKE 'COMPLETED' ORDER BY o.orderStatus")
    List<Order> findAll();

}

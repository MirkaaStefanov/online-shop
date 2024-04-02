package com.example.onlineshop.Order;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}

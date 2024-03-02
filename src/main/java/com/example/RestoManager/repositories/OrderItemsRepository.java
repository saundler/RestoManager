package com.example.RestoManager.repositories;

import com.example.RestoManager.models.Dish;
import com.example.RestoManager.models.Order;
import com.example.RestoManager.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
    List<OrderItem> findByOrderAndDish(Order order, Dish dish);
}

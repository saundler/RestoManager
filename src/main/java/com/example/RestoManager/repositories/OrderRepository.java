package com.example.RestoManager.repositories;

import com.example.RestoManager.models.Order;
import com.example.RestoManager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>  {
    Optional<Order> findById(Long id);
    List<Order> findByUser(User user);
}

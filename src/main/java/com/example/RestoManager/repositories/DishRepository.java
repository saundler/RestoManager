package com.example.RestoManager.repositories;

import com.example.RestoManager.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByTitle(String title);
    List<Dish> findByQuantityGreaterThan(int quantity);
    List<Dish> findByTitleContainingAndQuantityGreaterThan(String title, int quantity);
}

package com.example.RestoManager.services;

import com.example.RestoManager.exceptions.DishException;
import com.example.RestoManager.models.Dish;
import com.example.RestoManager.repositories.DishRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DishService.class);
    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) throws DishException {
        this.dishRepository = dishRepository;
        //addDish(new Dish("CEG", 1, 2, 3));
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    public List<Dish> listDishes(String title) {
        if (title != null && !title.isEmpty()) {
            return dishRepository.findByTitle(title);
        }
        return dishRepository.findAll();
    }

    public List<Dish> getMenu(String title) {
        if (title != null) {
            return dishRepository.findByTitleContainingAndQuantityGreaterThan(title, 0);
        } else {
            return dishRepository.findByQuantityGreaterThan(0);
        }
    }
    private void checkDish(Dish dish) throws DishException {
        String newTitle = dish.getTitle();
        // Проверка нового названия блюда
        if(newTitle == ""){
            throw new DishException("Название блюда не может быть пустым");
        }
        // Проверка новой цены блюда
        if (dish.getPrice() < 0) {
            throw new DishException("Цена блюда не может быть отрицательной");
        }
        // Проверка нового количества блюд
        if (dish.getQuantity() < 0) {
            throw new DishException("Количество блюд не может быть отрицательным");
        }
        // Проверка нового времени приготовления блюда
        if (dish.getCookingTimeInMinutes() < 0) {
            throw new DishException("Время приготовления  блюда не может быть отрицательным");
        }
    }

    public void addDish(Dish dish) throws DishException {
        checkDish(dish);
        if (!dishRepository.findByTitle(dish.getTitle()).isEmpty()) {
            throw new DishException("Блюдо с таким названием уже существует");
        }
        dishRepository.save(dish);
        log.info("Saving new {}", dish);
    }

    public void editDish(Long id, Dish newDishParameters) throws DishException {
        Dish dish = dishRepository.getReferenceById(id);
        String newTitle = newDishParameters.getTitle();
        checkDish(newDishParameters);
        if (!newTitle.equals(dish.getTitle()) && !dishRepository.findByTitle(newTitle).isEmpty()) {
            throw new DishException("Блюдо с таким названием уже существует");
        }
        dish.copy(newDishParameters);
        // Обновление параметров блюда в базе даннх
        dishRepository.save(dish);
        log.info("Edit dish by id {}", id);
    }

    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
        log.info("Delete dish by id {}", id);
    }
}

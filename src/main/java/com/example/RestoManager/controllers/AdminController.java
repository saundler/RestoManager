package com.example.RestoManager.controllers;

import com.example.RestoManager.models.Dish;
import com.example.RestoManager.services.DishService;
import com.example.RestoManager.services.OrderService;
import com.example.RestoManager.services.RestaurantService;
import com.example.RestoManager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final OrderService orderService;
    private final DishService dishService;
    private final RestaurantService restaurantService;

    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("revenue", restaurantService.getRevenue());
        List<Dish> dishes = dishService.listDishes(null);
        Comparator<Dish> soldComparator = Comparator.comparingInt(Dish::getSold).reversed();
        Collections.sort(dishes, soldComparator);
        model.addAttribute("dishes", dishes);
        model.addAttribute("orders", orderService.listOrders(null));
        return "statistics";
    }
}

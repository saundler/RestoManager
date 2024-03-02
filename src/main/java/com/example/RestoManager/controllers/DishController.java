package com.example.RestoManager.controllers;

import com.example.RestoManager.exceptions.DishException;
import com.example.RestoManager.models.Dish;
import com.example.RestoManager.models.enums.Role;
import com.example.RestoManager.services.DishService;
import com.example.RestoManager.services.UserService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class DishController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DishController.class);
    private final DishService dishService;
    private final UserService userService;

    public DishController(DishService dishService, UserService userService) {
        this.dishService = dishService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showMenu(@RequestParam(name = "title", required = false) String title, Model model, Principal principal) {
        model.addAttribute("menu", dishService.getMenu(title));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("ROLE_ADMIN", Role.ROLE_ADMIN);
        return "menu";
    }

    @GetMapping("/dish")
    public String getDishes(@RequestParam(name = "title", required = false) String title, Model model, Principal principal) {
        model.addAttribute("dishes", dishService.listDishes(title));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("ROLE_ADMIN", Role.ROLE_ADMIN);
        return "dishes";
    }

    @GetMapping("/dish/{id}")
    public String dishInfo(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("dish", dishService.getDishById(id));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("ROLE_ADMIN", Role.ROLE_ADMIN);
        return "info/dish-info";
    }

    @PostMapping("/dish/create")
    public String createDish(Dish dish, RedirectAttributes redirectAttributes) {
        try {
            dishService.addDish(dish);
        } catch (DishException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        return "redirect:/dish";
    }

    @PostMapping("/dish/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return "redirect:/dish";
    }

    @PostMapping("/dish/edit/{id}")
    public String editDish(@PathVariable Long id, Dish dish, Model model, Principal principal) {
        try {
            dishService.editDish(id, dish);
        } catch (DishException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
        }
        model.addAttribute("dish", dishService.getDishById(id));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("ROLE_ADMIN", Role.ROLE_ADMIN);
        return "info/dish-info";
    }
}
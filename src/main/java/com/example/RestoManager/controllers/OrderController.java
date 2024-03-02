package com.example.RestoManager.controllers;

import com.example.RestoManager.exceptions.OrderException;
import com.example.RestoManager.models.Order;
import com.example.RestoManager.models.User;
import com.example.RestoManager.services.DishService;
import com.example.RestoManager.services.OrderService;
import com.example.RestoManager.services.UserService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class OrderController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final DishService dishService;
    private final UserService userService;

    public OrderController(OrderService orderService, DishService dishService, UserService userService) {
        this.orderService = orderService;
        this.dishService = dishService;
        this.userService = userService;
    }

    @GetMapping("/order")
    public String showOrders(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("orders", orderService.listOrders(user));
        model.addAttribute("menu", dishService.getMenu(null));
        return "orders";
    }

    @GetMapping("/order/{id}")
    public String orderInfo(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("menu", dishService.getMenu(null));
        return "info/order-info";
    }

    @PostMapping("/order/create")
    public String createOrder(Principal principal) {
        Order order =  new Order(userService.getUserByPrincipal(principal));
        orderService.addOrder(order);
        return "redirect:/order/" + order.getId();
    }

    @PostMapping("/order/create/{id}")
    public String createOrder(@PathVariable Long id) {
        orderService.createOrder(id);
        return "redirect:/order";
    }

    @PostMapping("/order/pay/{id}")
    public String payOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            orderService.payOrder(id);
        } catch (OrderException exception){
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/order/" + id;
        }
        return "redirect:/order";
    }

    @PostMapping("/order/review/{id}")
    public String reviewOrder(@PathVariable Long id, int review, String comment, RedirectAttributes redirectAttributes) {
        try {
            orderService.reviewOrder(id, review, comment);
        } catch (OrderException exception){
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/order/" + id;
        }
        return "redirect:/order";
    }

    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteOrder(id);
        } catch (OrderException exception){
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/order/" + id;
        }
        return "redirect:/order";
    }
}

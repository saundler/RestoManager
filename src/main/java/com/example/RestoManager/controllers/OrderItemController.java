package com.example.RestoManager.controllers;

import com.example.RestoManager.exceptions.OrderItemException;
import com.example.RestoManager.models.OrderItem;
import com.example.RestoManager.services.DishService;
import com.example.RestoManager.services.OrderItemService;
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
import java.util.List;

@Controller
public class OrderItemController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OrderItemController.class);
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final UserService userService;


    public OrderItemController(OrderItemService orderItemService, OrderService orderService, DishService dishService, UserService userService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/order/{order_id}/orderItem/{orderItem_id}")
    public String orderItemInfo(@PathVariable Long orderItem_id, Model model, Principal principal) {
        model.addAttribute("orderItem", orderItemService.getOrderItemById(orderItem_id));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "info/orderItem-info";
    }

    @PostMapping("/order/{order_id}/orderItem/create")
    public String createOrderItem(@PathVariable Long order_id, OrderItem orderItem, RedirectAttributes redirectAttributes) {
        List<OrderItem> orderItems = orderItemService.listOrderItems(orderService.getOrderById(order_id), orderItem.getDish());
        if (!orderItems.isEmpty()) {
            try {
                orderItemService.editOrderItemQuantity(orderItems.get(0).getId(), orderItems.get(0).getQuantity() + orderItem.getQuantity());
            } catch (OrderItemException exception) {
                redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            }
        } else {
            try {
                orderItemService.addOrderItem(orderItem);
            } catch (OrderItemException exception) {
                redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            }
        }
        return "redirect:/order/" + order_id;
    }

    @PostMapping("/order/{order_id}/orderItem/delete/{orderItem_id}")
    public String deleteOrderItem(@PathVariable Long order_id, @PathVariable Long orderItem_id, RedirectAttributes redirectAttributes) {
        try {
            orderItemService.deleteOrderItem(orderItem_id);
        } catch (OrderItemException exception){
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/order/" + order_id + "/orderItem/" + orderItem_id;
        }
        return "redirect:/order/" + order_id;
    }

    @PostMapping("/order/{order_id}/orderItem/editQuantity/{orderItem_id}")
    public String editOrderItemQuantity(@PathVariable Long order_id, @PathVariable Long orderItem_id, int quantity, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            orderItemService.editOrderItemQuantity(orderItem_id, quantity);
        } catch (OrderItemException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        model.addAttribute("orderItem", orderItemService.getOrderItemById(orderItem_id));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "redirect:/order/" + order_id;
    }
}

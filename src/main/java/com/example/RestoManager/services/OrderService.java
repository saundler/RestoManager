package com.example.RestoManager.services;

import com.example.RestoManager.exceptions.OrderException;
import com.example.RestoManager.OrderStatusUpdater;
import com.example.RestoManager.models.Order;
import com.example.RestoManager.models.Restaurant;
import com.example.RestoManager.models.User;
import com.example.RestoManager.models.enums.OrderStatus;
import com.example.RestoManager.repositories.OrderRepository;
import com.example.RestoManager.repositories.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> listOrders(User user) {
        if (user != null) {
            return orderRepository.findByUser(user);
        }
        return orderRepository.findAll();
    }

    public void addOrder(Order order) {
        log.info("Saving new {}", order);
        orderRepository.save(order);
    }

    public void updateOrderStatus(Order order, OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }

    public void createOrder(Long id) {
        Order order = orderRepository.getReferenceById(id);
        updateOrderStatus(order, OrderStatus.STATUS_ACCEPTED);
        OrderStatusUpdater.updateOrderStatus(order, this);
    }

    public void payOrder(Long id) throws OrderException {
        Order order = orderRepository.getReferenceById(id);
        if (!order.getOrderStatus().equals(OrderStatus.STATUS_READY)) {
            throw new OrderException("Нельзя оплатить незавершенный заказ");
        }
        Restaurant restaurant;
        try {
            restaurant = restaurantRepository.findAll().get(0);
        } catch (Exception ex) {
            restaurant = new Restaurant();
        }
        restaurant.addToRevenue(order.getTotalPrice());
        restaurantRepository.save(restaurant);
        order.setPaid(true);
        orderRepository.save(order);
    }

    public void reviewOrder(Long id, int review, String comment) throws OrderException {
        Order order = orderRepository.getReferenceById(id);
        if (!order.getOrderStatus().equals(OrderStatus.STATUS_READY)) {
            throw new OrderException("Нельзя оставить отзыв для незавершенного заказа");
        }
        if (review > 5 || review < 1) {
            throw new OrderException("Оценка должна быть от 1 до 5");
        }
        order.setReview(review);
        order.setComment(comment);
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) throws OrderException {
        Order order = orderRepository.getReferenceById(id);
        if (order.getOrderStatus().equals(OrderStatus.STATUS_READY)) {
            throw new OrderException("Нельзя отменить завершенный заказ");
        }
        orderRepository.deleteById(id);
    }
}

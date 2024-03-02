package com.example.RestoManager.services;

import com.example.RestoManager.exceptions.OrderItemException;
import com.example.RestoManager.models.Dish;
import com.example.RestoManager.models.Order;
import com.example.RestoManager.models.OrderItem;
import com.example.RestoManager.models.enums.OrderStatus;
import com.example.RestoManager.repositories.DishRepository;
import com.example.RestoManager.repositories.OrderItemsRepository;
import com.example.RestoManager.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemsRepository orderItemsRepository;
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    public OrderItemService(OrderItemsRepository orderItemsRepository, OrderRepository orderRepository, DishRepository dishRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    public List<OrderItem> listOrderItems(Order order, Dish dish) {
        if (dish != null) {
            return orderItemsRepository.findByOrderAndDish(order, dish);
        }
        return orderItemsRepository.findByOrder(order);
    }


    public OrderItem getOrderItemById(Long id) {
        return orderItemsRepository.findById(id).orElse(null);
    }

    public void addOrderItem(OrderItem orderItem) throws OrderItemException {
        Order order = orderItem.getOrder();
        if(order.getOrderStatus().equals(OrderStatus.STATUS_READY)){
            throw new OrderItemException("Нельзя добавить элемент к завершенному заказу.");
        }
        int quantity = orderItem.getQuantity();
        // Проверка нового количества блюд
        if (quantity < 0) {
            throw new OrderItemException("Количество блюд не может быть отрицательным");
        } else if (quantity == 0) {
            return;
        }

        Dish orderItemDish = orderItem.getDish();

        int dishQuantity = orderItemDish.getQuantity();
        if (dishQuantity < quantity) {
            throw new OrderItemException("Нельзя добавить в заказ " + quantity +
                    " блюд, так как осталось всего " + dishQuantity + " блюд");
        }
        orderItem.updateTotalCost();
        orderItemDish.setSold(orderItemDish.getSold() + quantity);
        orderItemDish.setQuantity(dishQuantity - quantity);
        dishRepository.save(orderItemDish);
        orderItemsRepository.save(orderItem);
        order.updateTotalPrice();
        orderRepository.save(order);
    }

    public void editOrderItemQuantity(Long id, int quantity) throws OrderItemException {
        OrderItem orderItem = orderItemsRepository.getById(id);
        Order order = orderItem.getOrder();
        if(order.getOrderStatus().equals(OrderStatus.STATUS_READY)){
            throw new OrderItemException("Нельзя изменить элемент завершенного заказа.");
        }
        // Проверка нового количества блюд
        if (quantity < 0) {
            throw new OrderItemException("Количество блюд не может быть отрицательным");
        } else if (quantity == 0) {
            deleteOrderItem(id);
            return;
        }

        int orderItemQuantity = orderItem.getQuantity();

        Dish orderItemDish = orderItem.getDish();
        if (quantity > orderItemQuantity) {
            if (orderItemDish.getQuantity() < quantity - orderItemQuantity) {
                throw new OrderItemException("Нельзя увеличить количество блюд до " + quantity +
                        " так как с учетом блюд которые уже были заказны осталось всего " +
                        (orderItemDish.getQuantity() + orderItemQuantity) + " блюд");
            }
            orderItemDish.setQuantity(orderItemDish.getQuantity() - (quantity - orderItemQuantity));
            orderItemDish.setSold(orderItemDish.getSold() + (quantity - orderItemQuantity));
        } else if (quantity < orderItemQuantity) {
            orderItemDish.setSold(orderItemDish.getSold() - (orderItemQuantity - quantity));
        }
        // Обновление количества блюд в базе данных
        dishRepository.save(orderItemDish);
        orderItem.setQuantity(quantity);
        orderItem.updateTotalCost();
        orderItemsRepository.save(orderItem);
        order.updateTotalPrice();
        orderRepository.save(order);
    }

    public void deleteOrderItem(Long id) throws OrderItemException {
        OrderItem orderItem = orderItemsRepository.getReferenceById(id);
        Order order = orderItem.getOrder();
        if(order.getOrderStatus().equals(OrderStatus.STATUS_READY)){
            throw new OrderItemException("Нельзя удалить элемент завершенного заказа.");
        }
        Dish orderItemDish = orderItem.getDish();
        orderItemDish.setSold(orderItemDish.getSold() - orderItem.getQuantity());
        dishRepository.save(orderItemDish);
        orderItemsRepository.deleteById(id);
        order.updateTotalPrice();
        orderRepository.save(order);
    }

}

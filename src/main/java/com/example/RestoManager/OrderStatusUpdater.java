package com.example.RestoManager;

import com.example.RestoManager.models.Order;
import com.example.RestoManager.models.enums.OrderStatus;
import com.example.RestoManager.services.OrderService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderStatusUpdater {
    private static ExecutorService executor;
    public static void setExecutor(ExecutorService executor) {
        OrderStatusUpdater.executor = executor;
    }

    public static void updateOrderStatus(Order order, OrderService orderService) {
        executor.submit(() -> {
            while (order.getOrderStatus() != OrderStatus.STATUS_READY) {
                try {
                    TimeUnit.SECONDS.sleep(4);
                    switch (order.getOrderStatus()) {
                        case STATUS_ACCEPTED:
                            orderService.updateOrderStatus(order, OrderStatus.STATUS_PREPARING);
                            break;
                        case STATUS_PREPARING:
                            orderService.updateOrderStatus(order, OrderStatus.STATUS_READY);
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

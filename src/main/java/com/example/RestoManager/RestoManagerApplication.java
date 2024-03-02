package com.example.RestoManager;

import com.example.RestoManager.models.Order;
import com.example.RestoManager.models.enums.OrderStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;





@SpringBootApplication
public class RestoManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestoManagerApplication.class, args);
        ExecutorService executor = Executors.newCachedThreadPool();
        OrderStatusUpdater.setExecutor(executor);
    }
}

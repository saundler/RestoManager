package com.example.RestoManager.services;

import com.example.RestoManager.models.Restaurant;
import com.example.RestoManager.repositories.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    public RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }
    public int getRevenue(){
        Restaurant restaurant;
        try {
            restaurant = restaurantRepository.findAll().get(0);
        } catch (Exception ex) {
            restaurant = new Restaurant();
            restaurantRepository.save(restaurant);
        }
        return restaurant.getRevenue();
    }
}

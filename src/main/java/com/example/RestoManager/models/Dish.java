package com.example.RestoManager.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dishes_seq")
    @SequenceGenerator(name = "dishes_seq", sequenceName = "dishes_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", columnDefinition = "text")
    private String title;
    @Column(name = "price")
    private int price;
    @Column(name = "cooking_time_in_minutes")
    private int cookingTimeInMinutes;
    @Column(name = "quantity")
    private int quantity;
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column(name = "sold")
    private int Sold = 0;

    public Dish(String title, int price, int cookingTimeInMinutes, int quantity) {
        this.id = 0L;
        this.title = title;
        this.price = price;
        this.cookingTimeInMinutes = cookingTimeInMinutes;
        this.quantity = quantity;
        this.orderItems = new ArrayList<>();
    }

    public Dish() {
    }

    public void copy(Dish other) {
        // Обновление навзвания блюда
        setTitle(other.getTitle());
        // Обновление цены блюда
        setPrice(other.getPrice());
        // Обновление количества блюд
        setQuantity(other.getQuantity());
        // Обновление времени приготовления блюда
        setCookingTimeInMinutes(other.getCookingTimeInMinutes());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCookingTimeInMinutes(int cookingTimeInMinutes) {
        this.cookingTimeInMinutes = cookingTimeInMinutes;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Dish)) return false;
        final Dish other = (Dish) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        if (this.getPrice() != other.getPrice()) return false;
        if (this.getCookingTimeInMinutes() != other.getCookingTimeInMinutes()) return false;
        final Object this$amount = this.getQuantity();
        final Object other$amount = other.getQuantity();
        if (this$amount == null ? other$amount != null : !this$amount.equals(other$amount)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Dish;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        result = result * PRIME + this.getPrice();
        result = result * PRIME + this.getCookingTimeInMinutes();
        final Object $amount = this.getQuantity();
        result = result * PRIME + ($amount == null ? 43 : $amount.hashCode());
        return result;
    }

    public String toString() {
        return "Dish(id=" + this.getId() + ", title=" + this.getTitle() + ", price=" + this.getPrice() + ", cookingTimeInMinutes=" + this.getCookingTimeInMinutes() + ", amount=" + this.getQuantity() + ")";
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public int getPrice() {
        return this.price;
    }

    public int getCookingTimeInMinutes() {
        return this.cookingTimeInMinutes;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getSold() {
        return this.Sold;
    }

    public void setSold(int Sold) {
        this.Sold = Sold;
    }
}

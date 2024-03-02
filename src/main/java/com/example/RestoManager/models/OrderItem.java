package com.example.RestoManager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_cost")
    private int totalCost;

    public OrderItem() {
    }

    public void updateTotalCost() {
        totalCost = dish.getPrice() * quantity;
    }

    public Long getId() {
        return this.id;
    }

    public Order getOrder() {
        return this.order;
    }

    public Dish getDish() {
        return this.dish;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderItem)) return false;
        final OrderItem other = (OrderItem) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$order = this.getOrder();
        final Object other$order = other.getOrder();
        if (this$order == null ? other$order != null : !this$order.equals(other$order)) return false;
        final Object this$dish = this.getDish();
        final Object other$dish = other.getDish();
        if (this$dish == null ? other$dish != null : !this$dish.equals(other$dish)) return false;
        if (this.getQuantity() != other.getQuantity()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OrderItem;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $order = this.getOrder();
        result = result * PRIME + ($order == null ? 43 : $order.hashCode());
        final Object $dish = this.getDish();
        result = result * PRIME + ($dish == null ? 43 : $dish.hashCode());
        result = result * PRIME + this.getQuantity();
        return result;
    }

    public String toString() {
        return "OrderItem(id=" + this.getId() + ", order=" + this.getOrder() + ", dish=" + this.getDish() + ", quantity=" + this.getQuantity() + ")";
    }

    public int getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}

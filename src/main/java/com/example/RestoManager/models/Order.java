package com.example.RestoManager.models;

import com.example.RestoManager.models.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.STATUS_CREATED;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "review")
    private int review;

    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public Order() {
    }

    public void updateTotalPrice() {
        totalPrice = 0;
        for (OrderItem item : items) {
            totalPrice += item.getTotalCost();
        }
    }

    public Order(User user) {
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public int getTotalPrice() {
        return this.totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public boolean isPaid() {
        return this.paid;
    }

    public int getReview() {
        return this.review;
    }

    public String getComment() {
        return this.comment;
    }

    public List<OrderItem> getItems() {
        return this.items;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Order)) return false;
        final Order other = (Order) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        if (this.getTotalPrice() != other.getTotalPrice()) return false;
        final Object this$orderStatus = this.getOrderStatus();
        final Object other$orderStatus = other.getOrderStatus();
        if (this$orderStatus == null ? other$orderStatus != null : !this$orderStatus.equals(other$orderStatus))
            return false;
        if (this.isPaid() != other.isPaid()) return false;
        if (this.getReview() != other.getReview()) return false;
        final Object this$comment = this.getComment();
        final Object other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) return false;
        final Object this$items = this.getItems();
        final Object other$items = other.getItems();
        if (this$items == null ? other$items != null : !this$items.equals(other$items)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Order;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + this.getTotalPrice();
        final Object $orderStatus = this.getOrderStatus();
        result = result * PRIME + ($orderStatus == null ? 43 : $orderStatus.hashCode());
        result = result * PRIME + (this.isPaid() ? 79 : 97);
        result = result * PRIME + this.getReview();
        final Object $comment = this.getComment();
        result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
        final Object $items = this.getItems();
        result = result * PRIME + ($items == null ? 43 : $items.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    public String toString() {
        return "Order(id=" + this.getId() + ", totalPrice=" + this.getTotalPrice() + ", orderStatus=" + this.getOrderStatus() + ", paid=" + this.isPaid() + ", review=" + this.getReview() + ", comment=" + this.getComment() + ", items=" + this.getItems() + ", user=" + this.getUser() + ")";
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}

package com.example.RestoManager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurants_seq")
    @SequenceGenerator(name = "restaurants_seq", sequenceName = "restaurants_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "revenue")
    private int revenue;

    public Restaurant() {
    }

    public void addToRevenue(int price) {
        revenue += price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getRevenue() {
        return this.revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Restaurant)) return false;
        final Restaurant other = (Restaurant) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        if (this.getRevenue() != other.getRevenue()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Restaurant;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + this.getRevenue();
        return result;
    }

    public String toString() {
        return "Restaurant(id=" + this.getId() + ", revenue=" + this.getRevenue() + ")";
    }
}

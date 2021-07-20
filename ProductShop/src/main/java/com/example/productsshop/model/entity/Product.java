package com.example.productsshop.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    //id, name (at least 3 characters), price, buyerId (optional) and sellerId as IDs of users.
    private String name;
    private BigDecimal price;
    private User buyer;
    private User seller;
    private Set<Category> categories;


    public Product() {
        this.categories = new HashSet<>();
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }


   @ManyToOne
    public User getBuyer() {
        return buyer;
    }


    @ManyToOne
    public User getSeller() {
        return seller;
    }

    @ManyToMany()
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}

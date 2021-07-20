package com.example.productsshop.model.dto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserSellerDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<ProductSuccessfullySold> soldProducts;

    public Set<ProductSuccessfullySold> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<ProductSuccessfullySold> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public UserSellerDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

package com.example.productsshop.model.dto;

import com.google.gson.annotations.Expose;

public class UsersAndProductsDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private Products products;

    public UsersAndProductsDto() {
    }

    public Products getListOfSoldProducts() {
        return products;
    }

    public void setListOfSoldProducts(Products products) {
        this.products = products;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



}

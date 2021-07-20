package com.example.productsshop.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UsersCountDto {
    @Expose
    private int usersCount;
    @Expose
    private List<UsersAndProductsDto> users;

    public UsersCountDto() {

    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<UsersAndProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersAndProductsDto> users) {
        this.users = users;
    }
}

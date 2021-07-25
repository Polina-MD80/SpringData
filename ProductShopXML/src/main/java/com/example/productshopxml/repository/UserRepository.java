package com.example.productshopxml.repository;

import com.example.productshopxml.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u " +
            "inner join Product p " +
            "on p.seller.id = u.id " +
            "where p.buyer is not null " +
            "order by u.lastName, u.firstName")
    List<User> findAllBySoldProductsMoreThanZero();

    @Query("SELECT u from User u " +
            "where u.soldProducts.size>0 " +
            "order by u.soldProducts.size desc, u.lastName")
    List<User> findAllUsersWithSoldProductsOrderedByNumberOfProductsDescAndLastName();
}

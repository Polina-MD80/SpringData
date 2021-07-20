package com.example.productsshop.repository;

import com.example.productsshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   @Query("select u from User u join Product p " +
           "on p.seller.id = u.id " +
           "where p.buyer is not null " +
           "order by u.lastName, u.firstName")
   List<User> findAllBySoldProductsMoreThanZero();


@Query("SELECT u from User u " +
        "where u.soldProducts.size>0 " +
        "order by u.soldProducts.size desc, u.lastName")
   List<User> findAllUsersWithSoldProductsOrderedByNumberOfProductsDescAndLastName();



}

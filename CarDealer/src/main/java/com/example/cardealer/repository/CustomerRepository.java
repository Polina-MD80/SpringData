package com.example.cardealer.repository;

import com.example.cardealer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    List<Customer> findAll();

    @Query("select c from Customer c where c.sales.size>0 ")
    List<Customer> findAllBySalesIsNotNull();

}

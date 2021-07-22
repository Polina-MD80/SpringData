package com.example.cardealer.repository;

import com.example.cardealer.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findCarByMakeOrderByModel(String make);

  @Query("select c from Car c join Sale s " +
          "on c.id = s.car.id ")
    List<Car> findAllSoldCars();

}

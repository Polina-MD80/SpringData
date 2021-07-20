package com.example.advquerying.Repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> getAllBySizeOrderById(Size sizeValue);

    @Query("select s from Shampoo s JOIN s.ingredients i where i.id in :parameter")
    List<Shampoo> asd(@Param(value = "parameter") List<Long> Ids);

    List<Shampoo> findShampooBySizeOrLabel_IdOrderByPrice(Size size, Long labelId);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceLessThan(BigDecimal price);

    @Query("select s from Shampoo s join s.ingredients i where i.name in :names")
    List<Shampoo> findAllShampooByIngredientNames(List<String> names);

    @Query("select s from Shampoo s where s.ingredients.size < :number")
    List<Shampoo> findShampooByNumberOfIngredients(int number);

}

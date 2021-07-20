package com.example.advquerying.Repositories;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query(value = "select s from Shampoo s " +
            "join s.ingredients i where i in :ingredients")
    List<Shampoo> findByIngredientsIn(@Param(value = "ingredients")
                                              Set<Ingredient> ingredients);


     List<Ingredient> findAllByNameStartingWith(String s);

     List<Ingredient> findAllByNameIn(Collection<String> names);

     @Query("delete from Ingredient i where i.name = :name")
     @Modifying
     void deleteAllByName(String name);

     @Query("UPDATE Ingredient i set i.price = i.price * 1.1")
    @Modifying
    void updateIngredientPriceBy10Percent();

     List<Ingredient> findAll();
}

package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.util.Collection;
import java.util.List;

public interface IngredientService {

    List<Ingredient> findAllByNameStartingWith(String s);

    List<Ingredient> findAllByNameIn(Collection<String> names);

    void deleteAllByName(String name);

    void updateIngredientPriceBy10Percent();

    List<Ingredient> findAll();
}

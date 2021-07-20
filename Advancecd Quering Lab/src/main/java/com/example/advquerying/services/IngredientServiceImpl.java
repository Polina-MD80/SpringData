package com.example.advquerying.services;

import com.example.advquerying.Repositories.IngredientRepository;
import com.example.advquerying.entities.Ingredient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAllByNameStartingWith(String s) {
        return this.ingredientRepository.findAllByNameStartingWith(s);
    }

    @Override
    public List<Ingredient> findAllByNameIn(Collection<String> names) {
        return this.ingredientRepository.findAllByNameIn(names);
    }

    @Override
    @Transactional
    public void deleteAllByName(String name) {
        this.ingredientRepository.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void updateIngredientPriceBy10Percent() {
        this.ingredientRepository.updateIngredientPriceBy10Percent();
    }

    @Override
    public List<Ingredient> findAll() {
        return this.ingredientRepository.findAll();
    }


}

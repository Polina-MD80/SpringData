package com.example.advquerying.services;

import com.example.advquerying.Repositories.ShampooRepository;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {
  private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }


    @Override
    public List<Shampoo> findByBrandAndSize(String brand, Size size) {
        return null;
    }

    @Override
    public List<Shampoo> getAllBySizeOrderById(Size sizeValue) {
        return shampooRepository.getAllBySizeOrderById(sizeValue);
    }

    @Override
    public List<Shampoo> findShampooBySizeOrLabel_IdOrderByPrice(Size size, Long labelId) {
        return this.shampooRepository.findShampooBySizeOrLabel_IdOrderByPrice(size,labelId);
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countAllByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public List<String> findAllShampooByIngredientNames(List<String> names) {
        return this.shampooRepository.findAllShampooByIngredientNames(names)
                .stream().map(Shampoo::getBrand).collect(Collectors.toList());
    }

    @Override
    public List<String> findShampooByNumberOfIngredients(int number) {
        return this.shampooRepository.findShampooByNumberOfIngredients(number).stream().map(Shampoo::getBrand).collect(Collectors.toList());
    }
}

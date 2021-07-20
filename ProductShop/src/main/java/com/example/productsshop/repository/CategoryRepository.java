package com.example.productsshop.repository;

import com.example.productsshop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
     @Query("select c from Category c " +
             "ORDER BY c.products.size desc ")
    List<Category> CategoriesByProductsCount();
}

package com.example.bookshopsystem.repository;

import com.example.bookshopsystem.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

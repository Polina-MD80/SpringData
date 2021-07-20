package com.example.bookshopsystem.service;

import com.example.bookshopsystem.model.entity.Category;
import com.example.bookshopsystem.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final String CATEGORIES_FILE_PATH = "src/main/resources/files/categories.txt";

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }
        Set<String> rows = Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))
                .stream()
                .filter(r -> !r.isEmpty())
                .collect(Collectors.toSet());
        rows.forEach(categoryName -> {
            Category category = new Category(categoryName);
            categoryRepository.save(category);
        });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        long numberOfCategories = random.nextInt(3) + 1;
        for (int i = 0; i < numberOfCategories; i++) {
            Random rnd = new Random();
            long id = rnd.nextInt(8) + 1;
            Category category = categoryRepository.findById(id).orElseThrow();
            categories.add(category);
        }
        return categories;
    }
}

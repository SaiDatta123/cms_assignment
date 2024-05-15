package com.wipro.service;


import com.wipro.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(int id);

    Category createCategory(Category category);

    void deleteCategory(int id);

    Category updateCategory(Category category);
}


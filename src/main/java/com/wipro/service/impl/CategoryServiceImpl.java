package com.wipro.service.impl;

import com.wipro.exceptions.ResourceNotFoundException;
import com.wipro.model.Category;
import com.wipro.repository.CategoryRepository;
import com.wipro.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        log.info("Fetching all categories from repository.");
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getId());
        if (existingCategory.isPresent()) {
            log.info("Updating Category for given id : {} ", existingCategory.get().getId());
            existingCategory.get().setCode(category.getCode());
            existingCategory.get().setDescription(category.getDescription());
            categoryRepository.save(existingCategory.get());
            return existingCategory.get();
        }else{
            throw new ResourceNotFoundException("Category not found with id :"+ category.getId());
        }
    }


}

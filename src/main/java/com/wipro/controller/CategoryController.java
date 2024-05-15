package com.wipro.controller;

import com.wipro.exceptions.CustomBadRequestException;
import com.wipro.exceptions.ResourceNotFoundException;
import com.wipro.model.Category;
import com.wipro.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cat")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Validated Category category) {
        log.info("Creating a Category.");
        Category savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Fetching all the categories from database.");
        List<Category> categoryList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int categoryId) {
        log.info("Fetching category details from database for the given id : {}",categoryId );
        Optional<Category> optionalCategory = categoryService.getCategoryById(categoryId);
        Category category = optionalCategory.
                orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + categoryId ));

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@Validated @RequestBody Category category) {
        log.info("Updating category details.");
        Category updatedCategory = null;
        try {
            int catId = category.getId();
            if (catId < 1) {
                throw new CustomBadRequestException("Invalid Id.ID must be not null and positive integer");
            }
            updatedCategory = categoryService.updateCategory(category);
        } catch (Exception e) {
            log.error("Error came while updating Category object \n {}", e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int categoryId) {
        log.info("Deleting the category for the given id : {} ", categoryId);
        if (categoryId < 1) {
            throw new CustomBadRequestException("Invalid Id.ID must be not null and positive integer");
        }
        Optional<Category> optionalCategory = categoryService.getCategoryById(categoryId);
        Category category = optionalCategory.
                orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + categoryId));
        log.info("Category exists with the given id : {} ", category.getId());
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category successfully deleted!", HttpStatus.OK);
    }

}



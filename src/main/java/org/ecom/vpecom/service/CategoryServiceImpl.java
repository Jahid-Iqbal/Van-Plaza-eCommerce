package org.ecom.vpecom.service;

import org.ecom.vpecom.model.Category;
import org.ecom.vpecom.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        category.setCategoryId(categoryId);
        existingCategory = categoryRepository.save(category);
        return existingCategory;
    }

    @Override
    public String deleteCategory(long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        categoryRepository.delete(existingCategory);
        return "Category deleted with id: " + categoryId;
    }
}

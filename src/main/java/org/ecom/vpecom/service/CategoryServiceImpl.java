package org.ecom.vpecom.service;

import org.ecom.vpecom.exception.ApiException;
import org.ecom.vpecom.exception.ResourceNotFoundException;
import org.ecom.vpecom.model.Category;
import org.ecom.vpecom.repository.CategoryRepository;
import org.springframework.stereotype.Service;

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
        List<Category> savedCategory = categoryRepository.findAll();
        if (savedCategory.isEmpty()) {
            throw new ApiException("No category has saved yet");
        }
        return savedCategory;
    }

    @Override
    public Optional<Category> getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public void createCategory(Category category) {
        Category catName = categoryRepository.findByCategoryName(category.getCategoryName());
        if (null !=catName)
            throw new ApiException("Category with name \"" + catName.getCategoryName() + "\" already exists");
        categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        category.setCategoryId(categoryId);
        existingCategory = categoryRepository.save(category);
        return existingCategory;
    }

    @Override
    public String deleteCategory(long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        categoryRepository.delete(existingCategory);
        return "Category deleted with id: " + categoryId;
    }
}

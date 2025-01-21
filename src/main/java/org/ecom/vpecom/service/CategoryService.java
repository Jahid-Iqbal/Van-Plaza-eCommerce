package org.ecom.vpecom.service;

import org.ecom.vpecom.model.Category;
import org.ecom.vpecom.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategories();
    Optional<Category> getCategoryById(long categoryId);
    void createCategory(Category category);
    Category updateCategory(Category category, long categoryId);
    String deleteCategory(long categoryId);

}

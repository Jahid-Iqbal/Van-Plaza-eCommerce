package org.ecom.vpecom.service;

import org.ecom.vpecom.dto.CategoryDTO;
import org.ecom.vpecom.dto.CategoryResponseDTO;
import org.ecom.vpecom.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryResponseDTO getAllCategories(int pageNumber, int dataLimit, String sortBy, String sortOrder);
    Optional<Category> getCategoryById(long categoryId);
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO updateCategory(CategoryDTO category, long categoryId);
    CategoryDTO deleteCategory(long categoryId);

}

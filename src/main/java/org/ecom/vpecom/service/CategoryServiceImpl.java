package org.ecom.vpecom.service;

import org.ecom.vpecom.dto.CategoryDTO;
import org.ecom.vpecom.dto.CategoryResponseDTO;
import org.ecom.vpecom.exception.ApiException;
import org.ecom.vpecom.exception.ResourceNotFoundException;
import org.ecom.vpecom.model.Category;
import org.ecom.vpecom.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CategoryResponseDTO getAllCategories() {
        List<Category> savedCategory = categoryRepository.findAll();
        if (savedCategory.isEmpty()) {
            throw new ApiException("No category has saved yet");
        }
        List<CategoryDTO> responseDTO = savedCategory.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setCategory(responseDTO);

        return response;
    }

    @Override
    public Optional<Category> getCategoryById(long categoryId) {
        Optional<Category> cat = categoryRepository.findById(categoryId);
        if (cat.isEmpty()) {
            throw new ResourceNotFoundException("Category","categoryId",categoryId);
        }
        return cat;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category catName = categoryRepository.findByCategoryName(category.getCategoryName());
        if (null !=catName)
            throw new ApiException("Category with name \"" + catName.getCategoryName() + "\" already exists");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, long categoryId) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        category.setCategoryId(categoryId);
        existingCategory = categoryRepository.save(category);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        categoryRepository.delete(existingCategory);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }
}

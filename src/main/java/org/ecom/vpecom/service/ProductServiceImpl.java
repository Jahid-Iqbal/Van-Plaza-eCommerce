package org.ecom.vpecom.service;

import org.ecom.vpecom.dto.ProductDTO;
import org.ecom.vpecom.dto.ProductResponseDTO;
import org.ecom.vpecom.exception.ResourceNotFoundException;
import org.ecom.vpecom.model.Category;
import org.ecom.vpecom.model.Product;
import org.ecom.vpecom.repository.CategoryRepository;
import org.ecom.vpecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    CategoryRepository CategoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        product.setCategory(category);
        product.setImage("Image.jpg");
        double sPrice = product.getPrice() - (product.getPrice() * (product.getDiscount()/100));
        product.setPrice(sPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponseDTO getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProducts(productDTOs);
        return productResponseDTO;
    }
}

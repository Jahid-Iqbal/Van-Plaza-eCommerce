package org.ecom.vpecom.service;

import org.ecom.vpecom.dto.ProductDTO;
import org.ecom.vpecom.dto.ProductResponseDTO;
import org.ecom.vpecom.exception.ApiException;
import org.ecom.vpecom.exception.ResourceNotFoundException;
import org.ecom.vpecom.model.Category;
import org.ecom.vpecom.model.Product;
import org.ecom.vpecom.repository.CategoryRepository;
import org.ecom.vpecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Value("${project.image}")
    private String path;

    @Autowired
    FileService fileService;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        boolean productExists = false;
        List<Product> products = category.getProducts();
        for (Product product : products) {
            if (product.getProductName().equals(productDto.getProductName())) {
                productExists = true;
            }
        }

        if (!productExists) {
            Product product = modelMapper.map(productDto, Product.class);
            product.setCategory(category);
            product.setImage("Image.jpg");
            double sPrice = product.getPrice() - (product.getPrice() * (product.getDiscount() / 100));
            product.setPrice(sPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }
        else {
            throw new ApiException("Product already exists");
        }
    }

    @Override
    public ProductResponseDTO getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        if(products.isEmpty())
            throw new ApiException("No products have been added yet.");

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProducts(productDTOs);
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO searchProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Product> products =productRepository.findProductsByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProducts(productDTOs);

        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO searchProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%"+ keyword + "%");
        List<ProductDTO> productResponseDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProducts(productResponseDTOS);
        return productResponseDTO;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        Product product = modelMapper.map(productDTO, Product.class);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setCategory(product.getCategory());
        Product updatedProduct = productRepository.save(existingProduct);

        return modelMapper.map(updatedProduct, ProductDTO.class);

    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
       Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
       productRepository.delete(product);
       return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product fetchedProductFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        String filename = fileService.uploadImage(path, image);
        fetchedProductFromDB.setImage(filename);
        Product updatedProduct = productRepository.save(fetchedProductFromDB);

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

}

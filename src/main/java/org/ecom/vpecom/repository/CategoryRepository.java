package org.ecom.vpecom.repository;

import org.ecom.vpecom.model.Category;
import org.ecom.vpecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}

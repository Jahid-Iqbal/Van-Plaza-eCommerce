package org.ecom.vpecom.repository;

import org.ecom.vpecom.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

package com.flower.d2c.repository;

import com.flower.d2c.model.Product;
import com.flower.d2c.model.Product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Id(Long categoryId);
    List<Product> findByType(ProductType type);
}

package com.flower.d2c.service;

import com.flower.d2c.model.Product;
import com.flower.d2c.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Lấy toàn bộ danh sách "Nhật Ký Cắt Hoa Tuần Này" (Hoa hỏa tốc)
     */
    public List<Product> getRetailProducts() {
        return productRepository.findByType(Product.ProductType.RETAIL_FLOWER);
    }
    
    /**
     * Lấy danh sách phụ kiện, bình hoa mộc
     */
    public List<Product> getMerchandise() {
        return productRepository.findByType(Product.ProductType.MERCHANDISE);
    }
}

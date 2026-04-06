package com.flower.d2c.service.impl;

import com.flower.d2c.model.Product;
import com.flower.d2c.repository.ProductRepository;
import com.flower.d2c.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getRetailProducts() {
        return Collections.emptyList(); // TODO: Map to Category
    }

    @Override
    public List<Product> getMerchandise() {
        return Collections.emptyList(); // TODO: Map to Category
    }
}

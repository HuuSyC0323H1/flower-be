package com.flower.d2c.service;

import com.flower.d2c.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getRetailProducts();

    List<Product> getMerchandise();
}
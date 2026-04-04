package com.flower.d2c.controller;

import com.flower.d2c.model.Product;
import com.flower.d2c.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/retail-harvest")
    public ResponseEntity<List<Product>> getWeeklyHarvest() {
        return ResponseEntity.ok(productService.getRetailProducts());
    }

    @GetMapping("/merchandise")
    public ResponseEntity<List<Product>> getVasesAndAccessories() {
        return ResponseEntity.ok(productService.getMerchandise());
    }
}

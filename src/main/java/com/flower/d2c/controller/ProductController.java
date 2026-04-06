package com.flower.d2c.controller;

import com.flower.d2c.controller.view.ResponseObject;
import com.flower.d2c.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Inventory", description = "Quản lý kho hoa tươi và phụ kiện.")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/retail-harvest")
    @Operation(summary = "Hoa tươi thu hoạch", description = "Lấy danh sách hoa lẻ tươi cắt tại vườn trong tuần này.")
    public ResponseObject getWeeklyHarvest() {
        return new ResponseObject(productService.getRetailProducts());
    }

    @GetMapping("/merchandise")
    @Operation(summary = "Phụ kiện đi kèm", description = "Xem danh sách bình hoa, kéo và các vật phẩm trang trí.")
    public ResponseObject getVasesAndAccessories() {
        return new ResponseObject(productService.getMerchandise());
    }
}

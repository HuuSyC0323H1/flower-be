package com.flower.d2c.controller.controller.apipri;

import com.flower.d2c.model.SubscriptionPlan;
import com.flower.d2c.repository.SubscriptionPlanRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/private/packages")
@Tag(name = "Admin Subscription Packages", description = "Quản lý các Gói Hoa Định Kỳ.")
public class SubscriptionPlanController {

    @Resource
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @GetMapping
    @Operation(summary = "Liệt kê gói dịch vụ", description = "Lấy toàn bộ các gói (Standard, DIY...) hiện đang có trong hệ thống.")
    public ResponseEntity<List<SubscriptionPlan>> getAllPackages() {
        return ResponseEntity.ok(subscriptionPlanRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Tạo gói mới", description = "Thêm một gói đăng ký định kỳ mới vào Website.")
    public ResponseEntity<SubscriptionPlan> createPackage(@RequestBody SubscriptionPlan plan) {
        return ResponseEntity.ok(subscriptionPlanRepository.save(plan));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Sửa thông tin gói", description = "Cập nhật giá, ảnh và tính năng HTML của gói hiện hữu.")
    public ResponseEntity<SubscriptionPlan> updatePackage(@PathVariable Long id, @RequestBody SubscriptionPlan plan) {
        plan.setId(id);
        return ResponseEntity.ok(subscriptionPlanRepository.save(plan));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa gói hoa", description = "Gỡ bỏ hoàn toàn một gói dịch vụ khỏi danh mục bán hàng.")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        subscriptionPlanRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

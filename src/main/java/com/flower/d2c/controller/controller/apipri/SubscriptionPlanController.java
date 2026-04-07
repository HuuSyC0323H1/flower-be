package com.flower.d2c.controller.controller.apipri;

import com.flower.d2c.controller.view.ResponseObject;
import com.flower.d2c.model.SubscriptionPlan;
import com.flower.d2c.service.SubscriptionPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/packages")
@Tag(name = "Admin Subscription Packages", description = "Quản lý các Gói Hoa Định Kỳ.")
public class SubscriptionPlanController {

    @Resource
    private SubscriptionPlanService subscriptionPlanService;

    @GetMapping
    @Operation(summary = "Liệt kê gói dịch vụ", description = "Lấy toàn bộ các gói (Standard, DIY...) hiện đang có trong hệ thống.")
    public ResponseObject getAllPackages() {
        return new ResponseObject();
    }

    @PostMapping
    @Operation(summary = "Tạo gói mới", description = "Thêm một gói đăng ký định kỳ mới vào Website.")
    public ResponseObject createPackage(@RequestBody SubscriptionPlan plan) {
        return new ResponseObject();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Sửa thông tin gói", description = "Cập nhật giá, ảnh và tính năng HTML của gói hiện hữu.")
    public ResponseObject updatePackage(@PathVariable Long id, @RequestBody SubscriptionPlan plan) {
        return new ResponseObject();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa gói hoa", description = "Gỡ bỏ hoàn toàn một gói dịch vụ khỏi danh mục bán hàng.")
    public ResponseObject deletePackage(@PathVariable Long id) {
        return new ResponseObject();
    }
}

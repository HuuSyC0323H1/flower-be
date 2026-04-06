package com.flower.d2c.controller;

import com.flower.d2c.model.Delivery;
import com.flower.d2c.model.UserSubscription;
import com.flower.d2c.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@Tag(name = "Customer Subscriptions", description = "Quản lý lượt đăng ký và lịch giao hoa của khách hàng.")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    @Operation(summary = "Đăng ký gói mới", description = "Khách hàng bắt đầu một chu kỳ đăng ký gói hoa định kỳ.")
    public ResponseEntity<UserSubscription> subscribe(
            @RequestParam Long userId, 
            @RequestParam Long planId) {
        
        UserSubscription subscription = subscriptionService.subscribeToPlan(userId, planId);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/delivery/{deliveryId}/pause")
    @Operation(summary = "Tạm dừng giao hàng", description = "Dùng trong trường hợp khách bận, muốn dời lịch giao hoa sang tuần kế tiếp.")
    public ResponseEntity<Delivery> pauseDelivery(@PathVariable Long deliveryId) {
        Delivery replacement = subscriptionService.pauseDelivery(deliveryId);
        return ResponseEntity.ok(replacement);
    }
}

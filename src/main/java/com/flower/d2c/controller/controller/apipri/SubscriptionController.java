package com.flower.d2c.controller.controller.apipri;

import com.flower.d2c.controller.view.ResponseObject;
import com.flower.d2c.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/subscription")
@Tag(name = "Customer Subscriptions", description = "Quản lý lượt đăng ký và lịch giao hoa của khách hàng.")
public class SubscriptionController {

    @Resource
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    @Operation(summary = "Đăng ký gói mới", description = "Khách hàng bắt đầu một chu kỳ đăng ký gói hoa định kỳ.")
    public ResponseObject subscribe(
            @RequestParam Long userId, 
            @RequestParam Long planId) {
        
        return new ResponseObject(subscriptionService.subscribeToPlan(userId, planId));
    }

    @PostMapping("/delivery/{deliveryId}/pause")
    @Operation(summary = "Tạm dừng giao hàng", description = "Dùng trong trường hợp khách bận, muốn dời lịch giao hoa sang tuần kế tiếp.")
    public ResponseObject pauseDelivery(@PathVariable Long deliveryId) {
        return new ResponseObject(subscriptionService.pauseDelivery(deliveryId));
    }
}

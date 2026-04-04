package com.flower.d2c.controller;

import com.flower.d2c.model.Delivery;
import com.flower.d2c.model.UserSubscription;
import com.flower.d2c.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<UserSubscription> subscribe(
            @RequestParam Long userId, 
            @RequestParam Long planId) {
        
        UserSubscription subscription = subscriptionService.subscribeToPlan(userId, planId);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/delivery/{deliveryId}/pause")
    public ResponseEntity<Delivery> pauseDelivery(@PathVariable Long deliveryId) {
        Delivery replacement = subscriptionService.pauseDelivery(deliveryId);
        return ResponseEntity.ok(replacement);
    }
}

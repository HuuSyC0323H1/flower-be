package com.flower.d2c.controller;

import com.flower.d2c.model.Invoice;
import com.flower.d2c.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/retail")
    public ResponseEntity<Invoice> expressCheckout(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Invoice.PaymentMethod paymentMethod) {

        Invoice newInvoice = checkoutService.directCheckoutRetail(userId, productId, paymentMethod);
        return ResponseEntity.ok(newInvoice);
    }
}

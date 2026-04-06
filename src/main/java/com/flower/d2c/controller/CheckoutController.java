package com.flower.d2c.controller;

import com.flower.d2c.model.Invoice;
import com.flower.d2c.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@Tag(name = "Checkout & Orders", description = "Xử lý thanh toán và hóa đơn bán lẻ.")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/retail")
    @Operation(summary = "Thanh toán hoa lẻ", description = "Tạo hóa đơn tức thì cho một sản phẩm hoa lẻ thu hoạch trong tuần.")
    public ResponseEntity<Invoice> expressCheckout(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Invoice.PaymentMethod paymentMethod) {

        Invoice newInvoice = checkoutService.directCheckoutRetail(userId, productId, paymentMethod);
        return ResponseEntity.ok(newInvoice);
    }
}

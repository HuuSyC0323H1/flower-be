package com.flower.d2c.controller.controller.apipub;

import com.flower.d2c.model.Invoice;
import com.flower.d2c.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/checkout")
@Tag(name = "Checkout & Orders", description = "Xử lý thanh toán và hóa đơn bán lẻ.")
public class CheckoutController {

    @Resource
    private CheckoutService checkoutService;

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

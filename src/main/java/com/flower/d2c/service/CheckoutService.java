package com.flower.d2c.service;

import com.flower.d2c.model.Invoice;

public interface CheckoutService {

    Invoice directCheckoutRetail(Long userId, Long variantId, Invoice.PaymentMethod method);
}

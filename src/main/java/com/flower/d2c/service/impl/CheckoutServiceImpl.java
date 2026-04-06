package com.flower.d2c.service.impl;

import com.flower.d2c.infrastructure.exception.NVException;
import com.flower.d2c.model.*;
import com.flower.d2c.repository.*;
import com.flower.d2c.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final UserRepository userRepository;
    private final VariantRepository variantRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final NotificationRepository notificationRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public Invoice directCheckoutRetail(Long userId, Long variantId, Invoice.PaymentMethod method) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Khách hàng"));

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Biến thể Sản phẩm không tồn tại"));
        
        Product product = variant.getProduct();

        // 1. Kiểm tra Tồn kho
        // TODO: Cập nhật kiểm tra và trừ ở bảng Inventory thay vì Product
        // (Tạm comment để code compile)
        /*
        if (inventory.getQuantity() <= 0) { ... }
        */

        Address addressToShip = null;
        if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
            addressToShip = user.getAddresses().get(0);
        }

        String orderCode = "HDD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        Order order = Order.builder()
                .user(user)
                .orderCode(orderCode)
                .recipientName(user.getFullName())
                .recipientPhone(user.getPhone())
                .shippingAddress(addressToShip)
                .orderType("RETAIL")
                .pickupMethod("DELIVERY")
                .totalAmount(variant.getPrice())
                .shippingFee(BigDecimal.ZERO)
                .status(Order.OrderStatus.CONFIRMED)
                .paymentMethod(method)
                .build();
        
        order = orderRepository.save(order);

        OrderItem item = OrderItem.builder()
                .order(order)
                .variant(variant)
                .productName(product.getName() + " - " + variant.getSku())
                .quantity(1)
                .unitPrice(variant.getPrice())
                .build();
        
        orderItemRepository.save(item);

        Invoice invoice = Invoice.builder()
                .user(user)
                .order(order)
                .transactionRef(orderCode)
                .totalAmount(order.getTotalAmount())
                .paymentMethod(method)
                .status(Invoice.InvoiceStatus.PENDING)
                .build();

        invoice = invoiceRepository.save(invoice);

        Notification notif = Notification.builder()
                .user(user)
                .title("Đơn hàng " + orderCode + " đã được xác nhận!")
                .message("Bạn đã chốt đơn thành công " + product.getName() + ". Shipper sẽ chuẩn bị lên đường ngay bây giờ.")
                .type(Notification.NotificationType.DELIVERY_ALERT)
                .build();
        notificationRepository.save(notif);

        return invoice;
    }
}

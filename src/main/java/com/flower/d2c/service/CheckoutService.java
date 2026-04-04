package com.flower.d2c.service;

import com.flower.d2c.model.*;
import com.flower.d2c.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final NotificationRepository notificationRepository;

    /**
     * Logic Xử lý Check-out (Mua Ngay) Hoa bán lẻ Hỏa Tốc.
     * Xác nhận đơn và Sinh mã phiếu thu (Invoice).
     */
    @Transactional
    public Invoice directCheckoutRetail(Long userId, Long productId, Invoice.PaymentMethod method) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Khách hàng"));
                
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm đã hết hàng hoặc bị gỡ bỏ"));

        // 1. Kiểm tra Tồn kho
        if (product.getStockQuantity() <= 0) {
            throw new RuntimeException("Rất tiếc! Số lượng hoa cắt lưới hôm nay đã bán hết.");
        }

        // 2. Trừ tồn kho hoa
        product.setStockQuantity(product.getStockQuantity() - 1);
        productRepository.save(product);

        // 3. Tạo Hóa Đơn Trực tiếp
        // Dùng Random UUID gồm 8 ký tự để gắn làm Mã Đơn (Vd: HDD-A1B2C3D4)
        String trackingCode = "HDD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Invoice invoice = Invoice.builder()
                .user(user)
                .transactionRef(trackingCode)
                .totalAmount(product.getPrice())
                .paymentMethod(method)
                .status(Invoice.InvoiceStatus.PENDING)
                .build();
                
        // Nếu chọn Trả Tiền Mặt, trạng thái Mặc định coi là Chờ Thanh Toán khi shipper tới.
        invoice = invoiceRepository.save(invoice);

        // 4. Kích hoạt Chuông Thông Báo cho User
        Notification notif = Notification.builder()
                .user(user)
                .title("Hóa Đơn Hoa Hỏa Tốc đã ghi nhận!")
                .message("Bạn đã chốt đơn thành công siêu phẩm " + product.getName() + " với giá " + product.getPrice() + ". Shipper sẽ chuẩn bị lên đường ngay bây giờ.")
                .type(Notification.NotificationType.DELIVERY_ALERT)
                .build();
        notificationRepository.save(notif);

        return invoice;
    }
}

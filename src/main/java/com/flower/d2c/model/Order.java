package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderCode; // HDD-XXXXXXXX

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private Invoice.PaymentMethod paymentMethod;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String recipientPhone;

    @Column(nullable = false, length = 50)
    private String orderType; // "RETAIL" or "SUBSCRIPTION"

    @Column(nullable = false, length = 50)
    private String pickupMethod; // "PICKUP" or "DELIVERY"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @Column(columnDefinition = "TEXT")
    private String note; // Lời chúc hoặc ghi chú giao hàng

    private LocalDateTime expectedDeliveryDate;

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PROCESSING,
        SHIPPING,
        DELIVERED,
        CANCELLED,
        REFUNDED
    }
}

package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_subscription_id", nullable = false)
    private UserSubscription userSubscription;

    private Integer weekNumber; // 1, 2, 3, 4
    
    private LocalDate scheduledDate;
    
    private LocalDateTime actualDeliveryDate;

    private String flowerTheme; // "Lan Hồ Điệp", "Mẫu Đơn Đỏ"

    private String shipperName;
    private String shipperPhone;
    private String deliveryPhotoUrl;
    
    @Column(columnDefinition = "TEXT")
    private String deliveryNote;

    @Enumerated(EnumType.STRING)
    private DeliveryTimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public enum DeliveryStatus {
        PENDING, 
        CONFIRMED,
        PROCESSING, 
        READY_FOR_PICKUP,
        SHIPPING, 
        DELIVERED, 
        SKIPPED, 
        FAILED,
        CANCELLED
    }

    public enum DeliveryTimeSlot {
        MORNING,    // 08:00 - 12:00
        AFTERNOON,  // 13:00 - 18:00
        EVENING     // 18:00 - 21:00
    }
}

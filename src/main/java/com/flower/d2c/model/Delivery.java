package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "deliveries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_subscription_id", nullable = false)
    private UserSubscription userSubscription;

    private Integer weekNumber; // 1, 2, 3, 4
    
    private LocalDate scheduledDate;
    
    private String flowerTheme; // "Lan Hồ Điệp", "Mẫu Đơn Đỏ"

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public enum DeliveryStatus {
        PENDING, PROCESSING, SHIPPED, DELIVERED, SKIPPED, CANCELLED
    }
}

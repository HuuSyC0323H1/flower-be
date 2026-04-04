package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliverySchedule {

    public enum DeliveryStatus {
        PENDING, PROCESSING, DELIVERED, PAUSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    private Integer weekNumber; // Vd: 1, 2, 3, 4
    
    private LocalDate expectedDeliveryDate;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    
    private String note;
}

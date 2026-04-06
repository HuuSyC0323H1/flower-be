package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_subscriptions")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscription extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private SubscriptionPlan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer totalDeliveries;
    private Integer completedDeliveries;

    private LocalDateTime startDate;
    private LocalDateTime nextDeliveryDate;

    private Boolean autoRenew;

    @Enumerated(EnumType.STRING)
    private Delivery.DeliveryTimeSlot preferredTimeSlot;

    public enum Status {
        ACTIVE, PAUSED, CANCELLED, COMPLETED, UPGRADED, EXPIRED
    }
}

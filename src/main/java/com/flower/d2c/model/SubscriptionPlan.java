package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    private Integer deliveriesPerCycle; // e.g., 4 (4 weeks)

    @Enumerated(EnumType.STRING)
    private PlanFrequency frequency; // WEEKLY, BI_WEEKLY, MONTHLY

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String features; // Rich text HTML features

    private String checkoutLink; // Zalo link

    public enum PlanFrequency {
        WEEKLY,
        BI_WEEKLY,
        MONTHLY
    }
}

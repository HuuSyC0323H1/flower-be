package com.flower.d2c.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan {
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

    private String frequency; // e.g., "WEEKLY"

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String features; // Rich text HTML features

    private String checkoutLink; // Zalo link
}

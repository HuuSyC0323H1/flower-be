package com.flower.d2c.controller.view.dto;

import com.flower.d2c.model.Product;
import com.flower.d2c.model.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteConfigView {

    private Long id;

    private String siteName;

    private String heroSlogan;

    private String heroImage;

    private List<Product> products;

    private List<SubscriptionPlan> subscriptionPlans;
}

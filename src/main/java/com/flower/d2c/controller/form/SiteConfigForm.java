package com.flower.d2c.controller.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteConfigForm {

    private Long id;

    private String siteName;

    private String heroSlogan;

    private String heroImage;

    private List<Long> productIds;

    private List<Long> subscriptionPlanIds;
}

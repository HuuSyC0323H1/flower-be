package com.flower.d2c.service.impl;

import com.flower.d2c.controller.form.SiteConfigForm;
import com.flower.d2c.controller.view.dto.SiteConfigView;
import com.flower.d2c.infrastructure.mapper.SiteConfigMapper;
import com.flower.d2c.model.Product;
import com.flower.d2c.model.SiteConfig;
import com.flower.d2c.model.SubscriptionPlan;
import com.flower.d2c.repository.ProductRepository;
import com.flower.d2c.repository.SiteConfigRepository;
import com.flower.d2c.repository.SubscriptionPlanRepository;
import com.flower.d2c.service.SiteConfigService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SiteConfigServiceImpl implements SiteConfigService {

    @Resource
    private SiteConfigRepository siteConfigRepository;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Resource
    private SiteConfigMapper siteConfigMapper;

    @Override
    public SiteConfigView getSiteConfig() {
        List<SiteConfig> siteConfig1 = siteConfigRepository.findAll();
        SiteConfig siteConfig = siteConfig1.get(0);
        List<Long> productIds = new ArrayList<>();
        List<Long> subcriptionPlandIds = new ArrayList<>();
        if (StringUtils.isNotBlank(siteConfig.getFeaturedIds())) {
            productIds = Arrays.stream(siteConfig.getFeaturedIds().split(",")).map(Long::parseLong).toList();
        }

        if (StringUtils.isNotBlank(siteConfig.getSubscriptionIds())) {
            subcriptionPlandIds = Arrays.stream(siteConfig.getSubscriptionIds().split(",")).map(Long::parseLong).toList();
        }

        List<Product> products = productRepository.findAllById(productIds);
        List<SubscriptionPlan> subscriptionPlans = subscriptionPlanRepository.findAllById(subcriptionPlandIds);

        return SiteConfigView.builder()
                .id(siteConfig.getId())
                .siteName(siteConfig.getSiteName())
                .heroImage(siteConfig.getHeroImage())
                .heroSlogan(siteConfig.getHeroSlogan())
                .products(products)
                .subscriptionPlans(subscriptionPlans)
                .build();
    }

    @Override
    public Boolean updateConfig(SiteConfigForm config) {
        SiteConfig siteConfig = siteConfigRepository.findById(config.getId()).orElse(null);
        if (siteConfig == null) {
            return false;
        }
        List<Product> products = productRepository.findAllById(config.getProductIds());
        if (products.isEmpty() || products.size() != config.getProductIds().size()) {
            return false;
        }

        List<SubscriptionPlan> subscriptionPlans = subscriptionPlanRepository.findAllById(config.getSubscriptionPlanIds());
        if (subscriptionPlans.isEmpty() || subscriptionPlans.size() != config.getSubscriptionPlanIds().size()) {
            return false;
        }
        siteConfigMapper.updateFromForm(config, siteConfig);
        siteConfigRepository.save(siteConfig);
        return true;
    }
}

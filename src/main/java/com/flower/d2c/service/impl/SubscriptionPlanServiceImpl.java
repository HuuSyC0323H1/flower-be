package com.flower.d2c.service.impl;

import com.flower.d2c.repository.SubscriptionPlanRepository;
import com.flower.d2c.service.SubscriptionPlanService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    @Resource
    private SubscriptionPlanRepository subscriptionPlanRepository;
}

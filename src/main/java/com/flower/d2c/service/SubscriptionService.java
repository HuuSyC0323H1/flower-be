package com.flower.d2c.service;

import com.flower.d2c.model.Delivery;
import com.flower.d2c.model.UserSubscription;

public interface SubscriptionService {

    UserSubscription subscribeToPlan(Long userId, Long planId);
    Delivery pauseDelivery(Long deliveryId);
    UserSubscription upgradeSubscription(Long userId, Long oldSubId, Long newPlanId);

}

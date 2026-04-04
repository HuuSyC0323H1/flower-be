package com.flower.d2c.repository;

import com.flower.d2c.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);
    List<Subscription> findByStatus(Subscription.SubStatus status);
}

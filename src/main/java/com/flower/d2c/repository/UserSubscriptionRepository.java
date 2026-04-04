package com.flower.d2c.repository;

import com.flower.d2c.model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    List<UserSubscription> findByUserId(Long userId);
}

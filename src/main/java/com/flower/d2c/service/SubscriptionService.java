package com.flower.d2c.service;

import com.flower.d2c.model.Delivery;
import com.flower.d2c.model.SubscriptionPlan;
import com.flower.d2c.model.User;
import com.flower.d2c.model.UserSubscription;
import com.flower.d2c.repository.DeliveryRepository;
import com.flower.d2c.repository.SubscriptionPlanRepository;
import com.flower.d2c.repository.UserRepository;
import com.flower.d2c.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionPlanRepository planRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final DeliveryRepository deliveryRepository;

    /**
     * Logic 1: Đăng ký Gói Hoa.
     * Thuật toán: Tự động phân bổ lịch giao hoa vào ngày Thứ 6 hàng tuần.
     */
    @Transactional
    public UserSubscription subscribeToPlan(Long userId, Long planId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        SubscriptionPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        // Tính toán ngày giao hàng tiếp theo (Thứ 6 tuần này hoặc tuần sau)
        LocalDate firstDelivery = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        UserSubscription subscription = UserSubscription.builder()
                .user(user)
                .plan(plan)
                .status(UserSubscription.Status.ACTIVE)
                .totalDeliveries(plan.getDeliveriesPerCycle())
                .completedDeliveries(0)
                .startDate(LocalDateTime.now())
                .nextDeliveryDate(firstDelivery.atStartOfDay())
                .build();

        subscription = userSubscriptionRepository.save(subscription);

        // Tạo sẵn các Lịch giao hàng (Deliveries) trong Database
        List<Delivery> deliveries = new ArrayList<>();
        for (int i = 0; i < plan.getDeliveriesPerCycle(); i++) {
            Delivery delivery = Delivery.builder()
                    .userSubscription(subscription)
                    .weekNumber(i + 1)
                    .scheduledDate(firstDelivery.plusWeeks(i)) // Mỗi bó cách nhau 1 tuần
                    .status(Delivery.DeliveryStatus.PENDING)
                    .build();
            deliveries.add(delivery);
        }
        
        deliveryRepository.saveAll(deliveries);
        return subscription;
    }

    /**
     * Logic 2: Khách hàng yêu cầu Tạm Dừng Giao Hoa trong tuần tới.
     * Thuật toán: Không xóa bó hoa, mà đẩy bó hoa đó xuống cuối kỳ. 
     * Ví dụ: Gói 4 tuần. Tạm dừng Tuần 3 -> Sẽ có thêm Lịch Tuần 5 để giao bù.
     */
    @Transactional
    public Delivery pauseDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        if (delivery.getStatus() != Delivery.DeliveryStatus.PENDING) {
            throw new RuntimeException("Chỉ có thể tạm dừng đợt giao hoa chưa bắt đầu xử lý.");
        }

        // 1. Chuyển trạng thái của đợt này thành BỎ QUA (SKIPPED)
        delivery.setStatus(Delivery.DeliveryStatus.SKIPPED);
        deliveryRepository.save(delivery);

        // 2. Tìm ngày giao của Đợt cuối cùng hiện tại trong Database
        List<Delivery> existingDeliveries = deliveryRepository
                .findByUserSubscriptionIdOrderByScheduledDateAsc(delivery.getUserSubscription().getId());
        
        Delivery lastScheduled = existingDeliveries.get(existingDeliveries.size() - 1);
        
        // 3. Tự động "cấy" thêm 1 tuần giao bù vào cuối danh sách
        Delivery replacementDelivery = Delivery.builder()
                .userSubscription(delivery.getUserSubscription())
                .weekNumber(lastScheduled.getWeekNumber() + 1)
                .scheduledDate(lastScheduled.getScheduledDate().plusWeeks(1))
                .status(Delivery.DeliveryStatus.PENDING)
                .build();

        return deliveryRepository.save(replacementDelivery);
    }

    /**
     * Logic 3: Nâng cấp Gói Hoa (Upgrade).
     * Thuật toán: Hủy các đợt giao chưa thực hiện của gói cũ và thay thế bằng gói mới.
     */
    @Transactional
    public UserSubscription upgradeSubscription(Long userId, Long oldSubId, Long newPlanId) {
        UserSubscription oldSubscription = userSubscriptionRepository.findById(oldSubId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (!oldSubscription.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized upgrade request");
        }

        if (oldSubscription.getStatus() != UserSubscription.Status.ACTIVE) {
            throw new RuntimeException("Chỉ có thể nâng cấp gói đang hoạt động.");
        }

        // 1. Đánh dấu gói cũ là đã được nâng cấp (UPGRADED)
        oldSubscription.setStatus(UserSubscription.Status.UPGRADED);
        userSubscriptionRepository.save(oldSubscription);

        // 2. Hủy các đợt giao hóa chưa thực xử lý (PENDING) của gói cũ
        List<Delivery> oldDeliveries = deliveryRepository.findByUserSubscriptionIdOrderByScheduledDateAsc(oldSubId);
        for (Delivery d : oldDeliveries) {
            if (d.getStatus() == Delivery.DeliveryStatus.PENDING) {
                d.setStatus(Delivery.DeliveryStatus.CANCELLED);
            }
        }
        deliveryRepository.saveAll(oldDeliveries);

        // 3. Đăng ký gói mới cho User
        return subscribeToPlan(userId, newPlanId);
    }
}

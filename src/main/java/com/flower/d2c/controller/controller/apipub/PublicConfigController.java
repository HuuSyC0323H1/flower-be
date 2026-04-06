package com.flower.d2c.controller.controller.apipub;

import com.flower.d2c.model.Product;
import com.flower.d2c.model.SiteConfig;
import com.flower.d2c.model.SubscriptionPlan;
import com.flower.d2c.model.Web2Block;
import com.flower.d2c.repository.ProductRepository;
import com.flower.d2c.repository.SiteConfigRepository;
import com.flower.d2c.repository.SubscriptionPlanRepository;
import com.flower.d2c.repository.Web2BlockRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/public/config")
@Tag(name = "Public API", description = "Endpoints cho khách hàng và trang landing page")
public class PublicConfigController {

    @Resource
    private SiteConfigRepository siteConfigRepository;

    @Resource
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Resource
    private Web2BlockRepository web2BlockRepository;

    @Resource
    private ProductRepository productRepository;

    @GetMapping("/site")
    @Operation(summary = "Lấy cấu hình trang chủ", description = "Trả về thông tin branding, slogan và banner chính của Website.")
    public ResponseEntity<SiteConfig> getSiteConfig() {
        return siteConfigRepository.findById(1L)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(SiteConfig.builder()
                        .siteName("Hoa Điểm Danh")
                        .heroSlogan("Hoa nở đúng độ, giao đúng lúc.")
                        .build()));
    }

    @GetMapping("/featured-products")
    @Operation(summary = "Lấy sản phẩm nổi bật", description = "Tra cứu các sản phẩm được Admin đánh dấu hiển thị ở trang chủ.")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        SiteConfig config = siteConfigRepository.findById(1L).orElse(null);
        if (config == null || config.getFeaturedIds() == null || config.getFeaturedIds().isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        
        List<Long> ids = Arrays.stream(config.getFeaturedIds().split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .toList();
                
        return ResponseEntity.ok(productRepository.findAllById(ids));
    }

    @GetMapping("/packages")
    @Operation(summary = "Danh sách gói hoa", description = "Lấy tất cả các gói đăng ký định kỳ (Standard, DIY...) đang mở.")
    public ResponseEntity<List<SubscriptionPlan>> getPackages() {
        return ResponseEntity.ok(subscriptionPlanRepository.findAll());
    }

    @GetMapping("/blocks")
    @Operation(summary = "Danh sách khối Web 2", description = "Lấy cấu trúc các khối dữ liệu để render trang vệ tinh (Headless).")
    public ResponseEntity<List<Web2Block>> getBlocks() {
        return ResponseEntity.ok(web2BlockRepository.findAll());
    }
}

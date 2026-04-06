package com.flower.d2c;

import com.flower.d2c.model.*;
import com.flower.d2c.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SiteConfigRepository siteConfigRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final Web2BlockRepository web2BlockRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            seedBasicWebData();
            seedAuthData();
        }
    }

    private void seedBasicWebData() {
        // ... (existing categories and products)
        Category hoaLe = categoryRepository.save(Category.builder().name("Hoa Lẻ").description("Hoa cắt cành bán lẻ").build());
        Category goiHoa = categoryRepository.save(Category.builder().name("Gói Định Kỳ").description("Các gói hoa giao hàng tháng").build());

        Product p1 = productRepository.save(Product.builder()
                .name("Cúc Mẫu Đơn Đỏ")
                .description("Cánh hoa dày dặn, độ nở rực rỡ")
                .price(new BigDecimal("120000"))
                .stockQuantity(15)
                .category(hoaLe)
                .type(Product.ProductType.RETAIL_FLOWER)
                .imageUrl("https://images.unsplash.com/photo-1563241527-3004b7be0ffd?w=300&q=80")
                .build());

        Product p2 = productRepository.save(Product.builder()
                .name("Lan Hồ Điệp Trắng")
                .description("Nhỏ gọn hoàn hảo cho góc làm việc")
                .price(new BigDecimal("250000"))
                .stockQuantity(5)
                .category(hoaLe)
                .type(Product.ProductType.RETAIL_FLOWER)
                .imageUrl("https://images.unsplash.com/photo-1505678261136-1933ba17f0bd?w=300&q=80")
                .build());

        Product p3 = productRepository.save(Product.builder()
                .name("Hoa Hồng Equador Mix")
                .description("Tone màu dâu tây ngọt ngào")
                .price(new BigDecimal("85000"))
                .stockQuantity(120)
                .category(hoaLe)
                .type(Product.ProductType.RETAIL_FLOWER)
                .imageUrl("https://images.unsplash.com/photo-1548839140-29a749e1bc4e?w=300&q=80")
                .build());

        subscriptionPlanRepository.save(SubscriptionPlan.builder()
                .name("Gói Thư Giãn (Standard)")
                .description("Nhận 4 hộp hoa phối sẵn mỗi tháng")
                .price(new BigDecimal("599000"))
                .deliveriesPerCycle(4)
                .frequency("WEEKLY")
                .imageUrl("/box-standard.png")
                .features("<ul class='package-features'><li>Nhận 4 hộp hoa phối sẵn mỗi tháng</li><li>Hoa cắt gốc, tỉa lá, bảo quản tuýp nước</li><li>Mix màu theo mùa</li></ul>")
                .checkoutLink("https://zalo.me/")
                .build());

        web2BlockRepository.save(Web2Block.builder()
                .name("Siêu Phẩm Mùa Cưới")
                .icon("💍")
                .description("Các dòng hoa cắt cành Cúc Mẫu Đơn tinh túy cho hôn lễ")
                .apiLink("/api/products/retail-harvest")
                .build());

        siteConfigRepository.save(SiteConfig.builder()
                .id(1L)
                .siteName("Hoa Điểm Danh")
                .heroSlogan("Khu vườn ngoại ô nằm gọn trên bàn làm việc của bạn")
                .heroImage("/hero-bg.jpg")
                .featuredIds(p1.getId() + "," + p2.getId() + "," + p3.getId())
                .build());
    }

    private void seedAuthData() {
        // 1. Privileges
        Privilege viewInv = privilegeRepository.save(Privilege.builder().name("VIEW_INVENTORY").build());
        Privilege updateStock = privilegeRepository.save(Privilege.builder().name("UPDATE_STOCK").build());
        Privilege viewRev = privilegeRepository.save(Privilege.builder().name("VIEW_REVENUE").build());

        // 2. Roles
        Role adminRole = roleRepository.save(Role.builder()
                .name("ROLE_SUPER_ADMIN")
                .description("Toàn quyền hệ thống")
                .privileges(java.util.Set.of(viewInv, updateStock, viewRev))
                .build());

        Role customerRole = roleRepository.save(Role.builder()
                .name("ROLE_CUSTOMER")
                .description("Khách mua hàng")
                .privileges(java.util.Set.of())
                .build());

        // 3. Super Admin User
        userRepository.save(User.builder()
                .fullName("Admin System")
                .email("admin@flower.com")
                .phone("0981234567")
                .password("admin123") // Note: In real app, password should be encoded
                .roles(java.util.Set.of(adminRole))
                .build());
    }
}

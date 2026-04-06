# Kế hoạch phát triển: Backend Hệ Thống Bán Hoa (D2C)

Dựa trên thiết kế ERD, quá trình xây dựng mã nguồn sẽ tuân theo các giai đoạn sau:

### Giai đoạn 1: Chuẩn hóa Model (Entities) & Repository
1. **RBAC & User**:
   - Cập nhật `User`, `Role`.
   - Tạo mới `Permission`, `RolePermission`.
   - Thêm `Tier`, `PointHistory`. Cập nhật `Address` (latitude, longitude).
2. **CMS/Ui**:
   - Cập nhật lại `Web2Block`, `SiteConfig` thành mô hình `Page` và `PageSection` hoặc làm tương thích.
3. **Product & Inventory**:
   - Tạo mới `Attribute`, `AttributeValue`, `Variant`, `VariantAttribute`, `ProductImage`.
   - Tạo mới bảng `Inventory`. Cập nhật `Product`.
4. **Subscription**:
   - Nâng cấp `SubscriptionPlan`, cấu trúc lại `UserSubscription`. Cài cắm `shipping_address_id`.
5. **Orders & Deliveries**:
   - Cập nhật lại logic của `Order`, giới thiệu `DeliverySchedule` (thay hoặc refactor `Delivery`). Đính kèm `shippingAddress`.
6. Tương ứng tạo tất cả các interface `*Repository` kế thừa `JpaRepository`.

### Giai đoạn 2: Phát triển Tầng Business (Service Layer)
- Refactor `ProductService`: Xử lý lưu các variant, tính toán tồn kho.
- Cập nhật `SubscriptionService`: Thiết lập package và định kỳ, logic đổi địa chỉ.
- Tích hợp `DeliveryService`: Thuật toán nhóm/tạo Lộ trình giao hàng dựa theo tọa độ (Latitude, Longitude) từ Address.
- Thiết lập `RBAC Service`: Khớp các annotations AOP để chặn API nếu không có Quyền hạn tương ứng.

### Giai đoạn 3: Tầng Controller & API (REST)
- Expose các Resource API chuẩn mực.
- Cập nhật Document với Swagger Open API (springdoc).
- Validation chặt chẽ dữ liệu đầu vào.

# Thiết Kế Cơ Sở Dữ Liệu - Hệ Thống Bán Bán Hoa (D2C)

Dựa trên yêu cầu của bạn, tôi đã cập nhật lại cấu trúc hệ thống: phân tách thuộc tính (attribute) ra thành bảng chuẩn rời (không dùng JSON), thêm hình ảnh sản phẩm, bổ sung tọa độ địa lý (vĩ độ, kinh độ) cho phần địa chỉ, và tích hợp một hệ thống phân quyền (RBAC) chi tiết cho cả khách hàng và quản trị viên.

## Đề Xuất Cấu Trúc Cơ Sở Dữ Liệu (Database Architecture)

Hệ thống được chia thành các phân hệ chính sau:

### 1. Phân hệ Phân quyền & Quản trị Người dùng (Auth & RBAC)
Sử dụng mô hình RBAC (Role-Based Access Control) để cấp quyền chi tiết.
- **`ROLES`**: Nhóm quyền (Ví dụ: `Customer`, `Super_Admin`, `Content_Editor`, `Order_Manager`).
- **`PERMISSIONS`**: Chi tiết tính năng hạn chế (Ví dụ: Resource `product`, Action `write` / `del`).
- **`ROLE_PERMISSIONS`**: Bảng trung gian ánh xạ Quyền hạn vào Nhóm quyền.
- **`USERS`**: Thông tin người dùng. `role_id` sẽ quyết định người dùng là Admin nhóm nào hay chỉ là Khách hàng.

### 2. Phân hệ Quản lý Địa chỉ & Loyalty (Addresses & Gamification)
- **`ADDRESSES`**: Chứa thông tin giao hàng bằng văn bản (`address_line`, `city`) kèm theo **`latitude`** và **`longitude`** (Backend tự động resolve theo text đã nhập).
- **`TIERS`**: Danh mục hạng thành viên (Nhỏ/Vừa/Lớn).
- **`POINT_HISTORY`**: Lịch sử cộng/trừ điểm thưởng. 

### 3. Phân hệ Cấu hình Giao diện (CMS - Landing Page & Web Shop)
- **`PAGES`**: Lưu trữ danh sách các trang.
- **`PAGE_SECTIONS`**: Các khối giao diện động trên trang, lưu cấu trúc theo `JSON` để Frontend tự do định hình nội dung.

### 4. Phân hệ Sản phẩm & Thuộc tính (Products & Dynamic Attributes)
Đã chuyển đổi hoàn toàn sang cấu trúc RDBMS thuần để lưu thuộc tính.
- **`CATEGORIES`**: Danh mục hoa.
- **`PRODUCTS`**: Sản phẩm gốc.
- **`PRODUCT_IMAGES`**: Hình ảnh sản phẩm (có thể chọn ảnh nào là `is_primary` - ảnh đại diện).
- **`VARIANTS`** (SKU): Các biến thể cụ thể (VD: Bó 10 bông, giá 500k).
- **`ATTRIBUTES`** & **`ATTRIBUTE_VALUES`**: Danh sách thuộc tính (VD: Thuộc tính "Kích thước" -> Giá trị: "Vừa", "Lớn").
- **`VARIANT_ATTRIBUTES`**: Bảng trung gian móc nối Variant với những giá trị thuộc tính tương ứng.

### 5. Phân hệ Giỏ hàng & Đơn hàng Bán lẻ (Cart & Order)
- Trạm trung chuyển tạo đơn thanh toán: **`CARTS`**, **`CART_ITEMS`**, **`ORDERS`**, **`ORDER_ITEMS`**.

### 6. Phân hệ Gói Dịch Vụ Hoa (Subscription)
Cho phép khách mua kỳ hạn.
- **`SUBSCRIPTION_PACKAGES`**: Cấu hình các loại gói với các chính sách.
- **`USER_SUBSCRIPTIONS`**: Quản lý gói mà user đang tham gia, được gán `shipping_address_id` mặc định để hệ thống tự biết nơi giao.

### 7. Phân hệ Lịch trình Giao hàng & Tồn kho (Logistics & Inventory)
- **`INVENTORY`**: Quản lý SL xuất nhập.
- **`DELIVERY_SCHEDULES`**: Điều phối ngày giao, lộ trình. Bản ghi có gắn trực tiếp `shipping_address_id` để query tọa độ (lat/long) làm thuật toán map lộ trình cực nhanh mà không cần JOIN nhiều bảng. Ưu tiên lộ trình gói định kỳ, hỏa tốc đối với đơn lẻ theo giờ đặt.

---

## Vẽ Sơ đồ Thực thể Liên kết (ERD)

```mermaid
erDiagram
    ROLES {
        int id PK
        string name "Customer, Admin, Editor"
        string description
    }
    PERMISSIONS {
        int id PK
        string resource "product, order..."
        string action "read, write, delete"
    }
    ROLE_PERMISSIONS {
        int role_id FK
        int permission_id FK
    }
    USERS {
        int id PK
        string email
        string phone
        string password
        int current_points
        int tier_id FK
        int role_id FK
        datetime created_at
    }
    TIERS {
        int id PK
        string name "Đồng, Bạc, Vàng"
        int min_points
        json benefits
    }
    ADDRESSES {
        int id PK
        int user_id FK
        string address_line
        string city
        decimal latitude "BE tự động lấy"
        decimal longitude
        boolean is_default
    }
    PAGES {
        int id PK
        string page_type "landing, shop"
        string title
        string slug
    }
    PAGE_SECTIONS {
        int id PK
        int page_id FK
        string component_type
        json content_data "Dữ liệu config UI"
        int display_order
        boolean is_active
    }
    CATEGORIES {
        int id PK
        string name
        int parent_id FK
    }
    PRODUCTS {
        int id PK
        string name
        int category_id FK
        boolean is_active
    }
    PRODUCT_IMAGES {
        int id PK
        int product_id FK
        string image_url
        boolean is_primary
        int display_order
    }
    ATTRIBUTES {
        int id PK
        string name "Ví dụ: Màu sắc, Kích Thước"
    }
    ATTRIBUTE_VALUES {
        int id PK
        int attribute_id FK
        string value "Đỏ, Xanh, Nhỏ, Lớn"
    }
    VARIANTS {
        int id PK
        int product_id FK
        string sku
        decimal price
    }
    VARIANT_ATTRIBUTES {
        int variant_id FK
        int attribute_value_id FK
    }
    INVENTORY {
        int id PK
        int variant_id FK
        int quantity 
        int reserved_quantity
    }
    SUBSCRIPTION_PACKAGES {
        int id PK
        string name
        decimal price
        int duration_days
        json policies
    }
    USER_SUBSCRIPTIONS {
        int id PK
        int user_id FK
        int package_id FK
        int shipping_address_id FK "Nơi nhận hàng mặc định"
        string status 
        datetime start_date
        datetime end_date
    }
    ORDERS {
        int id PK
        int user_id FK
        decimal total_amount
        string order_type "retail, subscription"
        string pickup_method "pickup, delivery"
        int shipping_address_id FK
        string status
        datetime created_at
    }
    ORDER_ITEMS {
        int id PK
        int order_id FK
        int variant_id FK
        int quantity
        decimal price
    }
    DELIVERY_SCHEDULES {
        int id PK
        int order_id FK "Null nếu đơn từ gói định kỳ"
        int user_subscription_id FK "Null nếu đơn lẻ"
        int shipping_address_id FK "Tham chiếu trực tiếp để map lộ trình"
        datetime scheduled_date
        string status "pending, out_for_delivery, done"
        int priority "1-Hotle, 2-ThuậnTiện"
        int delivery_person_id FK
    }
    POINT_HISTORY {
        int id PK
        int user_id FK
        int points 
        string reason 
        datetime created_at
    }

    ROLES ||--o{ USERS : "assigned to"
    ROLES ||--o{ ROLE_PERMISSIONS : "bao gồm"
    PERMISSIONS ||--o{ ROLE_PERMISSIONS : "được cấp quyền"

    USERS ||--o{ ADDRESSES : "có địa chỉ (lat/long)"
    USERS }|--|| TIERS : "thuộc hạng"
    USERS ||--o{ POINT_HISTORY : "tích điểm"
    USERS ||--o{ ORDERS : "đặt hàng"
    USERS ||--o{ USER_SUBSCRIPTIONS : "đăng ký dịch vụ"
    
    PAGES ||--o{ PAGE_SECTIONS : "chứa các khối UI"
    
    CATEGORIES ||--o{ PRODUCTS : "phân loại"
    PRODUCTS ||--o{ PRODUCT_IMAGES : "danh sách ảnh"
    PRODUCTS ||--o{ VARIANTS : "bao gồm"
    
    ATTRIBUTES ||--o{ ATTRIBUTE_VALUES : "sở hữu giá trị"
    VARIANTS ||--o{ VARIANT_ATTRIBUTES : "có thuộc tính"
    ATTRIBUTE_VALUES ||--o{ VARIANT_ATTRIBUTES : "sử dụng bởi"

    VARIANTS ||--o| INVENTORY : "lưu kho"
    SUBSCRIPTION_PACKAGES ||--o{ USER_SUBSCRIPTIONS : "tạo ra"
    ORDERS ||--o{ ORDER_ITEMS : "chi tiết"
    VARIANTS ||--o{ ORDER_ITEMS : "bán trong"
    ORDERS ||--o{ DELIVERY_SCHEDULES : "tạo lịch giao"
    USER_SUBSCRIPTIONS ||--o{ DELIVERY_SCHEDULES : "tạo lịch định kỳ"
    ADDRESSES ||--o{ USER_SUBSCRIPTIONS : "địa chỉ nhận gói"
    ADDRESSES ||--o{ DELIVERY_SCHEDULES : "điểm đến"
```

## User Review Required
> [!IMPORTANT]
> - Các thuộc tính biến thể hiện tại đã được tách thành `ATTRIBUTES`, `ATTRIBUTE_VALUES`, và `VARIANT_ATTRIBUTES` để dễ truy vấn qua SQL và lọc (phục vụ filter dễ dàng trên giao diện).
> - Tính năng tọa độ (`latitude`, `longitude`) đã được thiết lập ở trong bảng **`ADDRESSES`**. Các tọa độ này sẽ sử dụng trong lộ trình cho Entity **`DELIVERY_SCHEDULES`**.
> - Khả năng phân quyền chi tiết đã được áp dụng với RBAC (`ROLES`, `PERMISSIONS`). Quản trị viên (Admin) hoàn toàn có thể được giới hạn quyền truy cập chi tiết (chỉ đọc Order, hoặc được viết Product...).
> 
> Nếu bản cập nhật ERD này đã phản ánh chính xác các mong muốn của bạn, vui lòng xác nhận để tôi có thể kết thúc pha thiết kế. Nếu bạn có bất kì thay đổi nào thêm, cứ cho tôi biết nhé!

package com.flower.d2c.controller;

import com.flower.d2c.controller.view.ResponseObject;
import com.flower.d2c.model.SiteConfig;
import com.flower.d2c.repository.SiteConfigRepository;
import com.flower.d2c.service.SiteConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/config/site")
@CrossOrigin("*")
@Tag(name = "Admin Site Config", description = "Quản lý cấu hình chung của website.")
public class SiteConfigController {

    @Resource
    private SiteConfigRepository siteConfigRepository;
    @Resource
    private SiteConfigService siteConfigService;

    @GetMapping
    @Operation(summary = "Xem cấu hình Admin", description = "Truy lục bản ghi cấu hình duy nhất dùng để biên tập trên trang quản trị.")
    public ResponseObject getConfig() {
        return new ResponseObject(siteConfigService.getSiteConfig());
    }


    @PutMapping
    @Operation(summary = "Cập nhật cấu hình", description = "Lưu lại các thay đổi về slogan, banner hoặc ID sản phẩm nổi bật từ Admin Panel.")
    public ResponseObject updateConfig(@RequestBody SiteConfig config) {
        config.setId(1L);
        return new ResponseObject(siteConfigRepository.save(config));
    }
}

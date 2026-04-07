package com.flower.d2c.controller.controller.apipri;

import com.flower.d2c.controller.form.SiteConfigForm;
import com.flower.d2c.controller.view.ResponseObject;
import com.flower.d2c.service.SiteConfigService;
import com.flower.d2c.service.Web2BlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController("configBlockWebPrivate")
@RequestMapping("/api/private/blocks")
@Tag(name = "Admin Web", description = "Quản lý các khối Heading động cho trang.")
public class ConfigBlockWebController {

    @Resource
    private Web2BlockService web2BlockService;
    @Resource
    private SiteConfigService siteConfigService;

    @GetMapping("/site")
    @Operation(summary = "Xem cấu hình Admin", description = "Truy lục bản ghi cấu hình duy nhất dùng để biên tập trên trang quản trị.")
    public ResponseObject getConfig() {
        return new ResponseObject(siteConfigService.getSiteConfig());
    }

    @PutMapping
    @Operation(summary = "Cập nhật cấu hình", description = "Lưu lại các thay đổi về slogan, banner hoặc ID sản phẩm nổi bật từ Admin Panel.")
    public ResponseObject updateConfig(@RequestBody SiteConfigForm config) {
        return new ResponseObject(siteConfigService.updateConfig(config));
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách Blocks", description = "Xem lại tất cả các khối (Headless) đã thiết lập.")
    public ResponseObject getAllBlocks() {
        return new ResponseObject(web2BlockService.findAllBlockWeb());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa khối", description = "Loại bỏ hoàn toàn một khối khỏi trang vệ tinh.")
    public ResponseObject deleteBlock(@PathVariable Long id) {
        return new ResponseObject(web2BlockService.deleteBlock(id));
    }
}

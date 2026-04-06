package com.flower.d2c.controller.controller.apipri;

import com.flower.d2c.model.Web2Block;
import com.flower.d2c.repository.Web2BlockRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/private/blocks")
@Tag(name = "Admin Web2 Blocks", description = "Quản lý các khối Heading động cho trang vệ tinh.")
public class Web2BlockController {

    @Resource
    private Web2BlockRepository web2BlockRepository;

    @GetMapping
    @Operation(summary = "Lấy danh sách Blocks", description = "Xem lại tất cả các khối (Headless) đã thiết lập.")
    public ResponseEntity<List<Web2Block>> getAllBlocks() {
        return ResponseEntity.ok(web2BlockRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Tạo khối mới", description = "Tạo một khối dữ liệu với icon, tên và API endpoint cho Site 2.")
    public ResponseEntity<Web2Block> createBlock(@RequestBody Web2Block block) {
        return ResponseEntity.ok(web2BlockRepository.save(block));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Sửa khối", description = "Sửa đổi nội dung khối hiện tại (ID).")
    public ResponseEntity<Web2Block> updateBlock(@PathVariable Long id, @RequestBody Web2Block block) {
        block.setId(id);
        return ResponseEntity.ok(web2BlockRepository.save(block));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa khối", description = "Loại bỏ hoàn toàn một khối khỏi trang vệ tinh.")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        web2BlockRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

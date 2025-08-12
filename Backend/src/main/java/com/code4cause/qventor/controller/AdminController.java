package com.code4cause.qventor.controller;

import com.code4cause.qventor.model.Admin;
import com.code4cause.qventor.model.Warehouse;
import com.code4cause.qventor.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ✅ Create new admin
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.createAdmin(admin));
    }

    // ✅ Get all admins
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // ✅ Get single admin by Supabase user ID
    @GetMapping("/{supabaseUserId}")
    public ResponseEntity<Admin> getAdminBySupabaseUserId(@PathVariable String supabaseUserId) {
        return ResponseEntity.ok(adminService.getAdminBySupabaseUserId(supabaseUserId));
    }

    // ✅ Update admin
    @PutMapping("/{supabaseUserId}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable String supabaseUserId, @RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.updateAdmin(supabaseUserId, admin));
    }

    // ✅ Add New Warehouse to Admin by Supabase User ID
    @PostMapping("/{supabaseUserId}/warehouses")
    public Admin addWarehouseToAdmin(
            @PathVariable String supabaseUserId,
            @RequestBody Warehouse warehouse
    ) {
        return adminService.addWarehouseToAdmin(supabaseUserId, warehouse);
    }


    // ✅ Delete admin
    @DeleteMapping("/{supabaseUserId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String supabaseUserId) {
        adminService.deleteAdmin(supabaseUserId);
        return ResponseEntity.ok("Admin deleted successfully");
    }
}

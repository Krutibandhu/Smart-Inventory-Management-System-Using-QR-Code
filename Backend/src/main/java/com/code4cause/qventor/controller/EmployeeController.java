package com.code4cause.qventor.controller;


import com.code4cause.qventor.model.Employee;
import com.code4cause.qventor.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Allow requests from any origin
@RestController
@RequestMapping("/api/employees") // Base URL for employee-related APIs
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * ✅ Add a new employee to an admin by Admin's Supabase User ID
     * POST: /api/employees/{adminSupabaseUserId}
     */
    @PostMapping("/{adminSupabaseUserId}")
    public ResponseEntity<Employee> addEmployeeToAdmin(
            @PathVariable String adminSupabaseUserId,
            @RequestBody Employee employee
    ) {
        return ResponseEntity.ok(employeeService.addEmployeeToAdmin(adminSupabaseUserId, employee));
    }

    /**
     * ✅ Get all employees for a specific admin
     * GET: /api/employees/admin/{adminSupabaseUserId}
     */
    @GetMapping("/admin/{adminSupabaseUserId}")
    public ResponseEntity<List<Employee>> getEmployeesByAdmin(@PathVariable String adminSupabaseUserId) {
        return ResponseEntity.ok(employeeService.getEmployeesByAdmin(adminSupabaseUserId));
    }

    /**
     * ✅ Get single employee by ID
     * GET: /api/employees/{employeeId}
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    /**
     * ✅ Get single employee by Supabase User ID
     * GET: /api/employees/supabase/{supabaseUserId}
     */
    @GetMapping("/supabase/{supabaseUserId}")
    public ResponseEntity<Employee> getEmployeeBySupabaseUserId(@PathVariable String supabaseUserId) {
        return ResponseEntity.ok(employeeService.getEmployeeBySupabaseUserId(supabaseUserId));
    }


    /**
     * ✅ Update employee details
     * PUT: /api/employees/{employeeId}
     */
    @PutMapping("/{supabaseUserId}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable String supabaseUserId,
            @RequestBody Employee updatedEmployee
    ) {
        return ResponseEntity.ok(employeeService.updateEmployee(supabaseUserId, updatedEmployee));
    }

    /**
     * ✅ Delete employee by ID
     * DELETE: /api/employees/{employeeId}
     */
    @DeleteMapping("/{supabaseUserId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String supabaseUserId) {
        employeeService.deleteEmployee(supabaseUserId);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}

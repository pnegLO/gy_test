package com.smartapartment.controller;

import com.smartapartment.service.LeaseService;
import com.smartapartment.dto.LeaseDTO;
import com.smartapartment.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {
    @Autowired
    private LeaseService leaseService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Success", leaseService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return leaseService.findById(id)
            .map(lease -> ResponseEntity.ok(new ApiResponse<>(true, "Success", lease)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LeaseDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Created successfully", leaseService.save(dto)));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id, @RequestBody boolean approved) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Approval updated", leaseService.approve(id, approved)));
    }

    @PostMapping("/{id}/terminate")
    public ResponseEntity<?> terminate(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Lease terminated", leaseService.terminate(id)));
    }

    @GetMapping("/expiring")
    public ResponseEntity<?> getExpiringLeases(@RequestParam LocalDate date) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Success", leaseService.findExpiringLeases(date)));
    }
} 
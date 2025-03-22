package com.smartapartment.controller;

import com.smartapartment.service.DeviceService;
import com.smartapartment.dto.DeviceDTO;
import com.smartapartment.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Success", deviceService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return deviceService.findById(id)
            .map(device -> ResponseEntity.ok(new ApiResponse<>(true, "Success", device)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DeviceDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Created successfully", deviceService.save(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DeviceDTO dto) {
        if (!deviceService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Updated successfully", deviceService.save(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!deviceService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        deviceService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", null));
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody DeviceStatus status) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Status updated", deviceService.updateStatus(id, status)));
    }
} 
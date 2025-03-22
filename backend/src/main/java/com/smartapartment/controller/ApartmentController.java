package com.smartapartment.controller;

import com.smartapartment.service.ApartmentService;
import com.smartapartment.dto.ApartmentDTO;
import com.smartapartment.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Success", apartmentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return apartmentService.findById(id)
            .map(apartment -> ResponseEntity.ok(new ApiResponse<>(true, "Success", apartment)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ApartmentDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Created successfully", apartmentService.save(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ApartmentDTO dto) {
        if (!apartmentService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Updated successfully", apartmentService.save(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!apartmentService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        apartmentService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Deleted successfully", null));
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<?> getDeviceStats(@PathVariable Long id) {
        if (!apartmentService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Success", apartmentService.getDeviceStats(id)));
    }
} 
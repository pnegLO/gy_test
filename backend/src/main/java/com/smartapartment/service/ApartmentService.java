package com.smartapartment.service;

import com.smartapartment.model.Apartment;
import com.smartapartment.repository.ApartmentRepository;
import com.smartapartment.dto.ApartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;
    
    @Autowired
    private DeviceRepository deviceRepository;

    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    public Optional<Apartment> findById(Long id) {
        return apartmentRepository.findById(id);
    }

    @Transactional
    public Apartment save(ApartmentDTO dto) {
        Apartment apartment = new Apartment();
        // 设置属性
        apartment.setName(dto.getName());
        apartment.setAddress(dto.getAddress());
        apartment.setArea(dto.getArea());
        apartment.setPrice(dto.getPrice());
        apartment.setStatus(dto.getStatus());
        apartment.setDescription(dto.getDescription());
        
        return apartmentRepository.save(apartment);
    }

    @Transactional
    public void delete(Long id) {
        apartmentRepository.deleteById(id);
    }

    public List<Apartment> findAvailable() {
        return apartmentRepository.findByStatus(ApartmentStatus.AVAILABLE);
    }

    public Map<String, Long> getDeviceStats(Long apartmentId) {
        Map<String, Long> stats = new HashMap<>();
        stats.put("online", deviceRepository.countByApartmentIdAndStatus(apartmentId, DeviceStatus.ONLINE));
        stats.put("offline", deviceRepository.countByApartmentIdAndStatus(apartmentId, DeviceStatus.OFFLINE));
        stats.put("maintenance", deviceRepository.countByApartmentIdAndStatus(apartmentId, DeviceStatus.MAINTENANCE));
        return stats;
    }
} 
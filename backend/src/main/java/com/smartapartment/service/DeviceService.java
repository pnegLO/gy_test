package com.smartapartment.service;

import com.smartapartment.model.Device;
import com.smartapartment.repository.DeviceRepository;
import com.smartapartment.dto.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id);
    }

    public List<Device> findByApartmentId(Long apartmentId) {
        return deviceRepository.findByApartmentId(apartmentId);
    }

    @Transactional
    public Device save(DeviceDTO dto) {
        Device device = new Device();
        device.setName(dto.getName());
        device.setType(dto.getType());
        device.setStatus(dto.getStatus());
        device.setApartment(dto.getApartment());
        device.setDescription(dto.getDescription());
        
        return deviceRepository.save(device);
    }

    @Transactional
    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }

    @Transactional
    public Device updateStatus(Long id, DeviceStatus status) {
        Device device = deviceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Device not found"));
        device.setStatus(status);
        return deviceRepository.save(device);
    }
} 
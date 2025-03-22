package com.smartapartment.repository;

import com.smartapartment.model.Device;
import com.smartapartment.model.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByApartmentId(Long apartmentId);
    
    List<Device> findByStatus(DeviceStatus status);
    
    @Query("SELECT COUNT(d) FROM Device d WHERE d.apartment.id = ?1 AND d.status = ?2")
    Long countByApartmentIdAndStatus(Long apartmentId, DeviceStatus status);
} 
package com.smartapartment.repository;

import com.smartapartment.model.Apartment;
import com.smartapartment.model.ApartmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    List<Apartment> findByStatus(ApartmentStatus status);
    
    @Query("SELECT a FROM Apartment a WHERE a.price BETWEEN ?1 AND ?2")
    List<Apartment> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT COUNT(a) FROM Apartment a WHERE a.status = ?1")
    Long countByStatus(ApartmentStatus status);
} 
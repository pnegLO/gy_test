package com.smartapartment.repository;

import com.smartapartment.model.Lease;
import com.smartapartment.model.LeaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByStatus(LeaseStatus status);
    
    List<Lease> findByTenantId(Long tenantId);
    
    @Query("SELECT l FROM Lease l WHERE l.endDate < ?1 AND l.status = 'ACTIVE'")
    List<Lease> findExpiringLeases(LocalDate date);
    
    @Query("SELECT SUM(l.monthlyRent) FROM Lease l WHERE l.status = 'ACTIVE'")
    BigDecimal calculateTotalMonthlyRent();
} 
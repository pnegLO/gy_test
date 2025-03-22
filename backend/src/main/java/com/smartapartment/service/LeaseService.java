package com.smartapartment.service;

import com.smartapartment.model.Lease;
import com.smartapartment.repository.LeaseRepository;
import com.smartapartment.dto.LeaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaseService {
    @Autowired
    private LeaseRepository leaseRepository;

    public List<Lease> findAll() {
        return leaseRepository.findAll();
    }

    public Optional<Lease> findById(Long id) {
        return leaseRepository.findById(id);
    }

    @Transactional
    public Lease save(LeaseDTO dto) {
        Lease lease = new Lease();
        lease.setApartment(dto.getApartment());
        lease.setTenantId(dto.getTenantId());
        lease.setStartDate(dto.getStartDate());
        lease.setEndDate(dto.getEndDate());
        lease.setMonthlyRent(dto.getMonthlyRent());
        lease.setDeposit(dto.getDeposit());
        lease.setStatus(LeaseStatus.PENDING);
        lease.setTerms(dto.getTerms());
        
        return leaseRepository.save(lease);
    }

    @Transactional
    public Lease approve(Long id, boolean approved) {
        Lease lease = leaseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lease not found"));
        lease.setStatus(approved ? LeaseStatus.ACTIVE : LeaseStatus.TERMINATED);
        return leaseRepository.save(lease);
    }

    @Transactional
    public Lease terminate(Long id) {
        Lease lease = leaseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lease not found"));
        lease.setStatus(LeaseStatus.TERMINATED);
        return leaseRepository.save(lease);
    }

    public List<Lease> findExpiringLeases(LocalDate date) {
        return leaseRepository.findExpiringLeases(date);
    }
} 
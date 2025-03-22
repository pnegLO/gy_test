package com.smartapartment.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "leases")
@Data
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
} 
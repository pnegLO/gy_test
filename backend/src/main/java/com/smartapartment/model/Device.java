package com.smartapartment.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "devices")
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
} 
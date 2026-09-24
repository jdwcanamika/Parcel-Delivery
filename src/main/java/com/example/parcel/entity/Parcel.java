package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="parcels")
public class Parcel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String trackingNumber;

    @Column(nullable=false)
    private String senderName;

    @Column(nullable=false)
    private String receiverName;

    @Column(nullable=false)
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ParcelStatus status;

    @Column(nullable=false)
    private LocalDateTime createdAt;

   }

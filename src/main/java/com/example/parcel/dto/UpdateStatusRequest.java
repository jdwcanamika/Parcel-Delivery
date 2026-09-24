package com.example.parcel.dto;
import com.example.parcel.entity.ParcelStatus;
import jakarta.validation.constraints.NotNull;
public record UpdateStatusRequest(
        @NotNull ParcelStatus status) {}

package com.example.parcel.dto;
import jakarta.validation.constraints.NotBlank;
public record CreateParcelRequest(
    @NotBlank String senderName,
    @NotBlank String receiverName,
    @NotBlank String deliveryAddress) {}

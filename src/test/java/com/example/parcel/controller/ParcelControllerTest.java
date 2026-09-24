package com.example.parcel.controller;


import com.example.parcel.dto.CreateParcelRequest;
import com.example.parcel.dto.UpdateStatusRequest;
import com.example.parcel.entity.Parcel;
import com.example.parcel.entity.ParcelStatus;
import com.example.parcel.service.ParcelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ParcelControllerTest {
    @InjectMocks ParcelController parcelController;
    @Mock
    ObjectMapper objectMapper;
    @Mock ParcelService service;

    @Test
    void shouldCreateParcel() {

        CreateParcelRequest request =
                new CreateParcelRequest(
                        "ABC",
                        "XYZ",
                        "Singapore"
                );

        Parcel savedParcel = new Parcel();
        savedParcel.setId(1L);
        savedParcel.setTrackingNumber("TRK001");
        savedParcel.setStatus(ParcelStatus.CREATED);

        when(service.saveParcel(any(CreateParcelRequest.class)))
                .thenReturn(savedParcel);

        ResponseEntity<String> response =
                parcelController.saveParcel(request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(
                "Parcel saved successfully",
                response.getBody()
        );

        verify(service).saveParcel(request);
    }


    @Test
    void shouldGetParcelById() {

        Parcel parcel = new Parcel();

        parcel.setId(1L);
        parcel.setTrackingNumber("TRK001");
        parcel.setStatus(ParcelStatus.CREATED);

        when(service.getById(1L))
                .thenReturn(parcel);

        Parcel result =  parcelController.getParcel(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(
                "TRK001",
                result.getTrackingNumber()
        );

        verify(service).getById(1L);
    }


    @Test
    void shouldGetParcelByTrackingNumber() {

        Parcel parcel = new Parcel();

        parcel.setId(1L);
        parcel.setTrackingNumber("TRK001");
        parcel.setStatus(ParcelStatus.IN_TRANSIT);

        when(service.getByTracking("TRK001"))
                .thenReturn(parcel);

        Parcel result =
                parcelController.tracking("TRK001");

        assertNotNull(result);
        assertEquals(
                "TRK001",
                result.getTrackingNumber()
        );
        assertEquals(
                ParcelStatus.IN_TRANSIT,
                result.getStatus()
        );

        verify(service).getByTracking("TRK001");
    }


    @Test
    void shouldGetAllParcels() {

        Parcel parcel1 = new Parcel();
        parcel1.setId(1L);
        parcel1.setTrackingNumber("TRK001");

        Parcel parcel2 = new Parcel();
        parcel2.setId(2L);
        parcel2.setTrackingNumber("TRK002");

        when(service.getAllParcel())
                .thenReturn(List.of(parcel1, parcel2));

        List<Parcel> result =
                parcelController.getAllParcel();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(
                "TRK001",
                result.get(0).getTrackingNumber()
        );

        assertEquals(
                "TRK002",
                result.get(1).getTrackingNumber()
        );

        verify(service).getAllParcel();
    }


    @Test
    void shouldUpdateParcelStatus() {

        UpdateStatusRequest request =
                new UpdateStatusRequest(
                        ParcelStatus.PICKED_UP
                );

        Parcel parcel = new Parcel();

        parcel.setId(1L);
        parcel.setTrackingNumber("TRK001");
        parcel.setStatus(ParcelStatus.PICKED_UP);

        when(service.updateStatus(
                1L,
                ParcelStatus.PICKED_UP
        )).thenReturn(parcel);

        Parcel result =
                parcelController.updateStatus(1L, request);

        assertNotNull(result);
        assertEquals(
                ParcelStatus.PICKED_UP,
                result.getStatus()
        );

        verify(service).updateStatus(
                1L,
                ParcelStatus.PICKED_UP
        );
    }


    @Test
    void shouldCancelParcel() {

        Parcel parcel = new Parcel();

        parcel.setId(1L);
        parcel.setTrackingNumber("TRK001");
        parcel.setStatus(ParcelStatus.CANCELLED);

        doNothing()
                .when(service)
                .cancel(1L);

        when(service.getById(1L))
                .thenReturn(parcel);

        Parcel result =
                parcelController.cancel(1L);

        assertNotNull(result);
        assertEquals(
                ParcelStatus.CANCELLED,
                result.getStatus()
        );

        verify(service).cancel(1L);
        verify(service).getById(1L);
    }
}

package com.example.parcel.service;

import com.example.parcel.dto.CreateParcelRequest;
import com.example.parcel.entity.Parcel;
import com.example.parcel.entity.ParcelStatus;
import com.example.parcel.repository.ParcelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParcelServiceTest {


    @InjectMocks
    private ParcelService parcelService;

    @Mock
    private ParcelRepository parcelRepository;

    @Test
    void saveParcelTest() {

        CreateParcelRequest request = new CreateParcelRequest(
                "ABC",
                "XYZ",
                "Singapore"
        );

        Parcel savedParcel = new Parcel();
        savedParcel.setId(1L);
        savedParcel.setTrackingNumber("TRK001");
        savedParcel.setSenderName("ABC");
        savedParcel.setReceiverName("XYZ");
        savedParcel.setDeliveryAddress("Singapore");
        savedParcel.setStatus(ParcelStatus.CREATED);

        when(parcelRepository.save(any(Parcel.class)))
                .thenReturn(savedParcel);

        Parcel result = parcelService.saveParcel(request);

        assertNotNull(result);
        assertEquals("TRK001", result.getTrackingNumber());
        assertEquals(ParcelStatus.CREATED, result.getStatus());

        verify(parcelRepository).save(any(Parcel.class));
    }

    @Test
    void shouldGetParcel() {

        Parcel parcel = new Parcel();

        parcel.setId(1L);
        parcel.setTrackingNumber("TRK001");

        when(parcelRepository.findById(1L))
                .thenReturn(Optional.of(parcel));

        Parcel result = parcelService.getById(1L);

        assertNotNull(result);
        assertEquals("TRK001", result.getTrackingNumber());

        verify(parcelRepository).findById(1L);
    }
}

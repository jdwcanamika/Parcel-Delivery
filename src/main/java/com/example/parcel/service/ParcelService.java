package com.example.parcel.service;

import com.example.parcel.dto.CreateParcelRequest;
import com.example.parcel.entity.Parcel;
import com.example.parcel.entity.ParcelStatus;
import com.example.parcel.exception.InvalidStatusException;
import com.example.parcel.exception.ParcelNotFoundException;
import com.example.parcel.repository.ParcelRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ParcelService {
    private final ParcelRepository repository;
    public ParcelService(ParcelRepository repository){this.repository=repository;}

    public Parcel saveParcel(CreateParcelRequest request) {

        Parcel p = new Parcel();

        p.setTrackingNumber(
                "TRK-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        p.setSenderName(request.senderName());
        p.setReceiverName(request.receiverName());
        p.setDeliveryAddress(request.deliveryAddress());
        p.setStatus(ParcelStatus.CREATED);
        p.setCreatedAt(LocalDateTime.now());
        try {
            return repository.save(p);

        } catch (Exception e) {
            log.error("Failed to save parcel with tracking number: {}",
                    p.getTrackingNumber(), e);
            throw new RuntimeException("Failed to save parcel", e);
        }
    }

    public Parcel getById(Long id){
        return repository.findById(id).orElseThrow(()->new ParcelNotFoundException("Parcel not found: "+id));
    }

    public Parcel getByTracking(String tracking){
        return repository.findByTrackingNumber(tracking).orElseThrow(()->new ParcelNotFoundException("Parcel not found: "+tracking));}

    public List<Parcel> getAllParcel(){
        return repository.findAll();
    }

    public Parcel updateStatus(Long id, ParcelStatus newStatus){
        Parcel p = getById(id);
        validateTransition(p.getStatus(),newStatus);
        p.setStatus(newStatus);
        return repository.save(p);
    }

    public void cancel(Long id){
        updateStatus(id, ParcelStatus.CANCELLED);
    }
    private void validateTransition(ParcelStatus current, ParcelStatus next){
        if(current==ParcelStatus.DELIVERED || current==ParcelStatus.CANCELLED) throw new InvalidStatusException("Cannot change status from "+current);
        boolean valid = switch(current){
            case CREATED -> next==ParcelStatus.PICKED_UP || next==ParcelStatus.CANCELLED;
            case PICKED_UP -> next==ParcelStatus.IN_TRANSIT || next==ParcelStatus.CANCELLED;
            case IN_TRANSIT -> next==ParcelStatus.OUT_FOR_DELIVERY || next==ParcelStatus.CANCELLED;
            case OUT_FOR_DELIVERY -> next==ParcelStatus.DELIVERED;
            default -> false;
        };
        if(!valid) throw new InvalidStatusException("Invalid transition: "+current+" -> "+next);
    }
}

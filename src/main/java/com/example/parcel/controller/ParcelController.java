package com.example.parcel.controller;

import com.example.parcel.dto.CreateParcelRequest;
import com.example.parcel.dto.UpdateStatusRequest;
import com.example.parcel.entity.Parcel;
import com.example.parcel.service.ParcelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {
    @Autowired
    ParcelService service;

    public ParcelController(ParcelService service){this.service=service;}

    @PostMapping("/saveParcel")
    public ResponseEntity<String> saveParcel( @Valid @RequestBody CreateParcelRequest request) {
        service.saveParcel(request);
        return ResponseEntity.ok("Parcel saved successfully");
    }

    @GetMapping("getParcel/{id}")
    public Parcel getParcel(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/tracking/{trackingNumber}")
    public Parcel tracking(@PathVariable String trackingNumber){
        return service.getByTracking(trackingNumber);
    }

    @GetMapping("/getAllParcel")
    public List<Parcel> getAllParcel(){
        List getAllParDtl =  service.getAllParcel();
        return getAllParDtl;
    }

    @PatchMapping("/{id}/status")
    public Parcel updateStatus(@PathVariable Long id,@Valid @RequestBody UpdateStatusRequest request){
        return service.updateStatus(id,request.status());
    }
    @PostMapping("/{id}/cancel")
    public Parcel cancel(@PathVariable Long id){
        service.cancel(id);
        return service.getById(id);
    }
}

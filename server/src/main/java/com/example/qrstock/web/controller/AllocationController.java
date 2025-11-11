package com.example.qrstock.web.controller;

import com.example.qrstock.domain.Allocation;
import com.example.qrstock.service.AllocationService;
import com.example.qrstock.service.dto.AllocationRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/allocations")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping
    public ResponseEntity<Allocation> allocate(@Valid @RequestBody AllocationRequest request) {
        return ResponseEntity.ok(allocationService.allocate(request));
    }
}



package com.example.qrstock.service;

import com.example.qrstock.domain.Allocation;
import com.example.qrstock.domain.OrderLine;
import com.example.qrstock.domain.Package;
import com.example.qrstock.repository.AllocationRepository;
import com.example.qrstock.repository.OrderLineRepository;
import com.example.qrstock.repository.PackageRepository;
import com.example.qrstock.service.dto.AllocationRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AllocationService {

    private final AllocationRepository allocationRepository;
    private final OrderLineRepository orderLineRepository;
    private final PackageRepository packageRepository;

    public AllocationService(AllocationRepository allocationRepository,
                             OrderLineRepository orderLineRepository,
                             PackageRepository packageRepository) {
        this.allocationRepository = allocationRepository;
        this.orderLineRepository = orderLineRepository;
        this.packageRepository = packageRepository;
    }

    @Transactional
    public Allocation allocate(AllocationRequest request) {
        OrderLine orderLine = orderLineRepository.findById(request.getOrderLineId())
                .orElseThrow(() -> new IllegalArgumentException("Order line not found"));
        Package pkg = packageRepository.findByShortCode(request.getPackageShortCode())
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));

        Allocation allocation = new Allocation();
        allocation.setOrderLine(orderLine);
        allocation.setPkg(pkg);
        allocation.setQtyPcs(request.getQtyPcs());
        return allocationRepository.save(allocation);
    }
}



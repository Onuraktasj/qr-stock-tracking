package com.example.qrstock.service;

import com.example.qrstock.repository.ProductTypeRepository;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LookupService {

    private final ProductTypeRepository productTypeRepository;

    public LookupService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public Map<String, Object> getLookups() {
        return Map.of("productTypes", productTypeRepository.findAll());
    }
}



package com.example.qrstock.web.controller;

import com.example.qrstock.service.LookupService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lookups")
public class LookupController {

    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getLookups() {
        return ResponseEntity.ok(lookupService.getLookups());
    }
}



package com.example.qrstock.web.controller;

import com.example.qrstock.domain.Package;
import com.example.qrstock.service.PackageService;
import com.example.qrstock.service.dto.PackageRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/packages")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public ResponseEntity<List<Package>> listPackages() {
        return ResponseEntity.ok(packageService.listPackages());
    }

    @PostMapping
    public ResponseEntity<Package> createPackage(@Valid @RequestBody PackageRequest request) {
        return ResponseEntity.ok(packageService.createPackage(request));
    }

    @GetMapping(value = "/{shortCode}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQrImage(@PathVariable String shortCode) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(packageService.getQrImage(shortCode));
    }

    @GetMapping("/{shortCode}/qr/payload")
    public ResponseEntity<Map<String, Object>> getQrPayload(@PathVariable String shortCode) {
        return ResponseEntity.ok(packageService.getQrPayload(shortCode));
    }
}



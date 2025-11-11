package com.example.qrstock.web.controller;

import com.example.qrstock.domain.StockLedger;
import com.example.qrstock.service.StockService;
import com.example.qrstock.service.dto.StockMovementRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/in")
    public ResponseEntity<StockLedger> stockIn(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.ok(stockService.stockIn(request));
    }

    @PostMapping("/move")
    public ResponseEntity<StockLedger> stockMove(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.ok(stockService.stockMove(request));
    }

    @PostMapping("/out")
    public ResponseEntity<StockLedger> stockOut(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.ok(stockService.stockOut(request));
    }

    @PostMapping("/count")
    public ResponseEntity<StockLedger> stockCount(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.ok(stockService.stockCount(request));
    }

    @PostMapping("/adjust")
    public ResponseEntity<StockLedger> stockAdjust(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.ok(stockService.stockAdjust(request));
    }
}



package com.example.qrstock.service;

import com.example.qrstock.repository.StockLedgerRepository;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final StockLedgerRepository stockLedgerRepository;

    public ReportService(StockLedgerRepository stockLedgerRepository) {
        this.stockLedgerRepository = stockLedgerRepository;
    }

    public Map<String, Object> stockReport() {
        long totalEntries = stockLedgerRepository.count();
        return Map.of("entries", totalEntries);
    }
}



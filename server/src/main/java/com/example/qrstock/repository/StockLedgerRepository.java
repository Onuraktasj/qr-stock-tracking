package com.example.qrstock.repository;

import com.example.qrstock.domain.StockLedger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLedgerRepository extends JpaRepository<StockLedger, Long> {
}



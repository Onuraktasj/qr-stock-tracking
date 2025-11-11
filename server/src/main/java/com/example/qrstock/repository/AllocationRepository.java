package com.example.qrstock.repository;

import com.example.qrstock.domain.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {
}



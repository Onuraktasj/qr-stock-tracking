package com.example.qrstock.repository;

import com.example.qrstock.domain.Package;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PackageRepository extends JpaRepository<Package, UUID> {
    Optional<Package> findByShortCode(String shortCode);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("select p from Package p where p.id = :id")
    Optional<Package> lockById(@Param("id") UUID id);
}



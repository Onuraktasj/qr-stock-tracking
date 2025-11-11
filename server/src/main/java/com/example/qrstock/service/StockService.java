package com.example.qrstock.service;

import com.example.qrstock.domain.Hall;
import com.example.qrstock.domain.Package;
import com.example.qrstock.domain.StockLedger;
import com.example.qrstock.domain.User;
import com.example.qrstock.domain.enums.MovementType;
import com.example.qrstock.repository.HallRepository;
import com.example.qrstock.repository.PackageRepository;
import com.example.qrstock.repository.StockLedgerRepository;
import com.example.qrstock.repository.UserRepository;
import com.example.qrstock.service.dto.StockMovementRequest;
import java.util.Optional;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StockService {

    private final PackageRepository packageRepository;
    private final StockLedgerRepository stockLedgerRepository;
    private final HallRepository hallRepository;
    private final UserRepository userRepository;

    public StockService(PackageRepository packageRepository,
                        StockLedgerRepository stockLedgerRepository,
                        HallRepository hallRepository,
                        UserRepository userRepository) {
        this.packageRepository = packageRepository;
        this.stockLedgerRepository = stockLedgerRepository;
        this.hallRepository = hallRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public StockLedger stockIn(StockMovementRequest request) {
        Package pkg = packageRepository.findByShortCode(request.getPackageShortCode())
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));
        return recordMovement(pkg, MovementType.IN, request);
    }

    @Transactional
    public StockLedger stockMove(StockMovementRequest request) {
        Package pkg = getLockedPackage(request);
        return recordMovement(pkg, MovementType.MOVE, request);
    }

    @Transactional
    public StockLedger stockOut(StockMovementRequest request) {
        try {
            Package pkg = getLockedPackage(request);
            return recordMovement(pkg, MovementType.OUT, request);
        } catch (OptimisticLockingFailureException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Concurrent OUT operation detected", ex);
        }
    }

    @Transactional
    public StockLedger stockCount(StockMovementRequest request) {
        Package pkg = getLockedPackage(request);
        return recordMovement(pkg, MovementType.COUNT, request);
    }

    @Transactional
    public StockLedger stockAdjust(StockMovementRequest request) {
        Package pkg = getLockedPackage(request);
        return recordMovement(pkg, MovementType.ADJUST, request);
    }

    private Package getLockedPackage(StockMovementRequest request) {
        return packageRepository.findByShortCode(request.getPackageShortCode())
                .flatMap(pkg -> packageRepository.lockById(pkg.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));
    }

    private StockLedger recordMovement(Package pkg, MovementType type, StockMovementRequest request) {
        StockLedger ledger = new StockLedger();
        ledger.setPkg(pkg);
        ledger.setMovementType(type);
        ledger.setQtyPcs(request.getQtyPcs());
        ledger.setRefDoc(request.getRefDoc());
        ledger.setNotes(request.getNotes());
        lookupHall(request.getFromHallId()).ifPresent(ledger::setFromHall);
        lookupHall(request.getToHallId()).ifPresent(ledger::setToHall);
        lookupUser(request.getActorUserId()).ifPresent(ledger::setActor);
        return stockLedgerRepository.save(ledger);
    }

    private Optional<Hall> lookupHall(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return hallRepository.findById(id);
    }

    private Optional<User> lookupUser(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return userRepository.findById(id);
    }
}



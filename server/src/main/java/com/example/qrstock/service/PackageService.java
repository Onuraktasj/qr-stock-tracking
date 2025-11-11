package com.example.qrstock.service;

import com.example.qrstock.domain.Machine;
import com.example.qrstock.domain.Package;
import com.example.qrstock.domain.ProductType;
import com.example.qrstock.repository.MachineRepository;
import com.example.qrstock.repository.PackageRepository;
import com.example.qrstock.repository.ProductTypeRepository;
import com.example.qrstock.service.dto.PackageRequest;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PackageService {

    private final PackageRepository packageRepository;
    private final ProductTypeRepository productTypeRepository;
    private final MachineRepository machineRepository;
    private final QrPayloadFactory qrPayloadFactory;
    private final QrCodeService qrCodeService;

    public PackageService(PackageRepository packageRepository,
                          ProductTypeRepository productTypeRepository,
                          MachineRepository machineRepository,
                          QrPayloadFactory qrPayloadFactory,
                          QrCodeService qrCodeService) {
        this.packageRepository = packageRepository;
        this.productTypeRepository = productTypeRepository;
        this.machineRepository = machineRepository;
        this.qrPayloadFactory = qrPayloadFactory;
        this.qrCodeService = qrCodeService;
    }

    @Transactional(readOnly = true)
    public List<Package> listPackages() {
        return packageRepository.findAll();
    }

    @Transactional
    public Package createPackage(PackageRequest request) {
        ProductType productType = productTypeRepository.findById(request.getProductTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown product type"));
        Machine machine = null;
        if (request.getMachineId() != null) {
            machine = machineRepository.findById(request.getMachineId())
                    .orElseThrow(() -> new IllegalArgumentException("Unknown machine"));
        }

        Package pkg = new Package();
        pkg.setProductType(productType);
        pkg.setPieces(request.getPieces());
        pkg.setLengthMm(request.getLengthMm());
        pkg.setMachine(machine);
        pkg.setHeatNo(request.getHeatNo());
        pkg.setLotNo(request.getLotNo());
        pkg.setPaketNo(request.getPaketNo());

        Map<String, Object> payload = qrPayloadFactory.createPayload(pkg);
        pkg.setShortCode(payload.get("pkg").toString());
        return packageRepository.save(pkg);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getQrPayload(String shortCode) {
        Package pkg = packageRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));
        return qrPayloadFactory.createPayload(pkg);
    }

    @Transactional(readOnly = true)
    public byte[] getQrImage(String shortCode) {
        Map<String, Object> payload = getQrPayload(shortCode);
        return qrCodeService.generate(payload);
    }
}



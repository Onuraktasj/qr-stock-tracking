package com.example.qrstock.service;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.qrstock.domain.Package;

@Component
public class QrPayloadFactory {

    private final ShortCodeService shortCodeService;
    private final HmacService hmacService;
    private final String signingSecret;

    public QrPayloadFactory(ShortCodeService shortCodeService,
                            HmacService hmacService,
                            @Value("${qr.signing-secret:change-me}") String signingSecret) {
        this.shortCodeService = shortCodeService;
        this.hmacService = hmacService;
        this.signingSecret = signingSecret;
    }

    public Map<String, Object> createPayload(Package pkg) {
        String shortCode = pkg.getShortCode();
        if (shortCode == null) {
            shortCode = shortCodeService.toShortCode(UUID.randomUUID());
        }

        String productTypeToken = pkg.getProductType() != null
                ? String.valueOf(pkg.getProductType().getId())
                : "unknown";

        String payload = String.join("|",
                shortCode,
                productTypeToken,
                String.valueOf(pkg.getPieces()),
                String.valueOf(pkg.getLengthMm()));
        String signature = hmacService.sign(payload, signingSecret);

        return Map.of(
                "pkg", shortCode,
                "pt", productTypeToken,
                "pcs", pkg.getPieces(),
                "len", pkg.getLengthMm(),
                "dt", OffsetDateTime.now().toString(),
                "sig", signature
        );
    }
}



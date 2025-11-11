package com.example.qrstock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class QrCodeService {

    private static final int QR_SIZE = 360;

    private final ObjectMapper objectMapper;
    private final QRCodeWriter qrCodeWriter = new QRCodeWriter();

    public QrCodeService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public byte[] generate(Map<String, Object> payload) {
        try {
            String content = objectMapper.writeValueAsString(payload);
            BitMatrix matrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return out.toByteArray();
        } catch (JsonProcessingException | WriterException e) {
            throw new IllegalStateException("Failed to generate QR content", e);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write QR image", e);
        }
    }
}


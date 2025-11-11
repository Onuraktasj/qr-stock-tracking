package com.example.qrstock.service;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ShortCodeService {

    private static final char[] BASE32_ALPHABET = "ABCDEFGHJKMNPQRSTUVWXYZ23456789".toCharArray();

    public String toShortCode(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        BigInteger number = new BigInteger(1, buffer.array());
        StringBuilder base32 = new StringBuilder();
        BigInteger base = BigInteger.valueOf(BASE32_ALPHABET.length);
        while (number.compareTo(BigInteger.ZERO) > 0) {
            int index = number.mod(base).intValue();
            base32.append(BASE32_ALPHABET[index]);
            number = number.divide(base);
        }
        while (base32.length() < 20) {
            base32.append('A');
        }
        base32.reverse();

        int checksum = mod97(base32.toString());
        String paddedChecksum = String.format("%02d", checksum);
        String payload = base32.substring(0, 12) + paddedChecksum;
        return payload.substring(0, 4) + "-" + payload.substring(4, 8) + "-" + payload.substring(8, 12);
    }

    private int mod97(String input) {
        int checksum = 0;
        for (int i = 0; i < input.length(); i++) {
            checksum = ((checksum * 31) + input.charAt(i)) % 97;
        }
        return checksum;
    }
}



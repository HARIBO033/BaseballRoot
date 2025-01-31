package com.baseball_root.member;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CreateUuid {

    public String createUuid() {
        // UUID 생성
        String uuidStr = UUID.randomUUID().toString();

        byte[] uuidStrBytes = uuidStr.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes;

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            hashBytes = md.digest(uuidStrBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
            if (sb.length() == 8) {
                break;
            }
        }

        return sb.toString();
    }
}

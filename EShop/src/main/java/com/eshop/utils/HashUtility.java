package com.eshop.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtility {
    public static final String USAGE_ALGORITHM = "SHA-256";

    public static String getHash(String str) {
        String hashString = "";

        try {
            MessageDigest md = MessageDigest.getInstance(USAGE_ALGORITHM);
            byte[] hash = md.digest(str.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b: hash) {
                sb.append(String.format("%02x", b));
            }

            hashString = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return hashString;
    }
}

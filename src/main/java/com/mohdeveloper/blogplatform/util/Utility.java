package com.mohdeveloper.blogplatform.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;
import java.util.Random;

public class Utility {

    public static String generateToken(int byteLength) {
        final SecureRandom secureRandom = new SecureRandom();
        final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding(); // Encoder for making the output URL-safe
        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes); // Generate secure random bytes
        return base64Encoder.encodeToString(randomBytes); // Encode bytes to URL-safe string
    }


    public static String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static String createEncodedURL(String baseURL, String token) {
        String encodedToken = encodeValue(token);
        return baseURL + "?token=" + encodedToken;
    }

    public static String generate6CharToken() {
        final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWER = UPPER.toLowerCase(Locale.ROOT);
        final String DIGITS = "0123456789";
        final String ALPHANUM = UPPER + LOWER + DIGITS;
        final Random random = new SecureRandom();
        final char[] symbols = ALPHANUM.toCharArray();
        final char[] buf = new char[6];
        final StringBuilder toke = new StringBuilder();


        for (int idx = 0; idx < buf.length; ++idx) {
          toke.append(symbols[random.nextInt(symbols.length)]);

        }
        return toke.toString();
    }

}

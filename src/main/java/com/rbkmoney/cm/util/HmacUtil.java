package com.rbkmoney.cm.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HmacUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";

    @SneakyThrows
    public static String encode(String secret, byte[] data) {
        if (secret == null || data == null) {
            throw new IllegalArgumentException("key/data can't be null");
        }

        final Mac hmac = Mac.getInstance(HMAC_SHA256);
        byte[] hmacSecretBytes = secret.getBytes(StandardCharsets.UTF_8);
        final SecretKeySpec secretKey = new SecretKeySpec(hmacSecretBytes, HMAC_SHA256);
        hmac.init(secretKey);
        byte[] res = hmac.doFinal(data);

        return Base64.encodeBase64URLSafeString(res);
    }

}

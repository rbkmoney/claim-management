package com.rbkmoney.cm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rbkmoney.cm.exception.BadContinuationTokenException;
import com.rbkmoney.cm.util.HmacUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
public class ContinuationTokenService {

    public static final String TOKEN_FIELD = "token";

    public static final String CONTINUATION_DATA_FIELD = "continuation_data";

    private final String secret;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> String buildToken(T continuationData, List<Object> parameters) {
        ObjectNode tokenNode = objectMapper.createObjectNode();
        tokenNode.put(TOKEN_FIELD, getValidationTokenByParameters(parameters));
        tokenNode.put(CONTINUATION_DATA_FIELD, objectMapper.writeValueAsBytes(continuationData));
        return Base64.getUrlEncoder().encodeToString(objectMapper.writeValueAsBytes(tokenNode));
    }

    public <T> T validateAndGet(String token, Class<T> type, List<Object> parameters) {
        try {
            JsonNode tokenNode = objectMapper.readTree(Base64.getUrlDecoder().decode(token.getBytes(StandardCharsets.UTF_8)));
            validateParameters(tokenNode.get(TOKEN_FIELD).asText(), parameters);
            return objectMapper.readValue(tokenNode.get(CONTINUATION_DATA_FIELD).binaryValue(), type);
        } catch (Exception ex) {
            throw new BadContinuationTokenException(ex);
        }
    }

    private void validateParameters(String validationToken, List<Object> parameters) {
        if (!validationToken.equals(getValidationTokenByParameters(parameters))) {
            throw new IllegalArgumentException("Failed to validate token parameters");
        }
    }

    @SneakyThrows
    private String getValidationTokenByParameters(List<Object> parameters) {
        return HmacUtil.encode(secret, objectMapper.writeValueAsBytes(parameters));
    }

}

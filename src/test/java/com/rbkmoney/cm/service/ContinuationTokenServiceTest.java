package com.rbkmoney.cm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.cm.exception.BadContinuationTokenException;
import com.rbkmoney.cm.pageable.ClaimPageRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.rbkmoney.cm.model.ClaimStatusEnum.pending;

public class ContinuationTokenServiceTest {

    private ContinuationTokenService continuationTokenService = new ContinuationTokenService("123", new ObjectMapper());

    @Test
    public void testBuildAndValidateToken() {
        List<Object> parameters = Arrays.asList(1, null, "kek", 1L, Arrays.asList(pending));
        String token = continuationTokenService.buildToken(new ClaimPageRequest(1, -1), parameters);
        continuationTokenService.validateAndGet(token, ClaimPageRequest.class, parameters);
    }

    @Test
    public void testBuildAndValidateTokenWithEmptyParameters() {
        List<Object> parameters = List.of();
        String token = continuationTokenService.buildToken("чекаем с пустым дном", parameters);
        continuationTokenService.validateAndGet(token, String.class, parameters);
    }

    @Test(expected = BadContinuationTokenException.class)
    public void testValidateWrongParameters() {
        String token = continuationTokenService.buildToken(123, Arrays.asList("1", "2"));
        continuationTokenService.validateAndGet(token, Integer.class, Arrays.asList("wrong"));
    }

    @Test(expected = BadContinuationTokenException.class)
    public void testValidateWrongToken() {
        continuationTokenService.validateAndGet("kek", Integer.class, Arrays.asList());
        continuationTokenService.validateAndGet("kasddsdfsgsdgdgek", Integer.class, Arrays.asList());
        continuationTokenService.validateAndGet("eyJ0b2tlbiI6ImtkR0h2QWI0LW5EMUh0c2djSWxJODA0QUNjUXZhdjFya1lncXBScE1VYlEiLCJjb250aW51YXRpb25fZGF0YSI6InJPMEFCWE55QUJGcVlYWmhMbXhoYm1jdVNXNTBaV2RsY2hMaW9LVDNnWWM0QWdBQlNRQUZkbUZzZFdWNGNVFhbUYyWVM1c1lXNW5MazUxYldKbGNvYXNsUjBMbE9DTEFnQUFlSEFBQUFCNyJ9", Integer.class, Arrays.asList());
    }

}

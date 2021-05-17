package com.rbkmoney.cm.search;

import com.rbkmoney.cm.model.ClaimModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ClaimPageSearchResponse {

    private final List<ClaimModel> claims;

    private final String token;

}

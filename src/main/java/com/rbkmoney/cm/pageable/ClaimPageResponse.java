package com.rbkmoney.cm.pageable;

import com.rbkmoney.cm.model.ClaimModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimPageResponse {

    private List<ClaimModel> claims;

    private String token;

}

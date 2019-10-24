package com.rbkmoney.cm.pageable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimPageRequest {

    private int page;

    private int limit;

}

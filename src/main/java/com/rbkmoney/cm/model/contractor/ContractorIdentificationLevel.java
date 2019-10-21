package com.rbkmoney.cm.model.contractor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContractorIdentificationLevel {

    none(100),
    partial(200),
    full(300);

    private final int level;

}

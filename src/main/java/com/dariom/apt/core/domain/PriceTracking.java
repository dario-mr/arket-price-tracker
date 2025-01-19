package com.dariom.apt.core.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceTracking(
    Long id,
    Instant createdAt,
    Boolean active,
    String link,
    String productName,
    BigDecimal desiredPrice
) {

}

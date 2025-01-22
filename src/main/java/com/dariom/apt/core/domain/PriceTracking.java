package com.dariom.apt.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PriceTracking {
    private Long id;
    private Instant createdAt;
    private Boolean active;
    private String link;
    private String productName;
    private BigDecimal desiredPrice;
}

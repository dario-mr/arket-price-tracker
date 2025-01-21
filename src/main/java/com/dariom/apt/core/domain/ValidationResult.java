package com.dariom.apt.core.domain;

public record ValidationResult(
        boolean isValid,
        String message
) {
}

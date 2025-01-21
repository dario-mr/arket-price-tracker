package com.dariom.apt.util;

import com.dariom.apt.core.domain.ValidationResult;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtil {

    public static ValidationResult validateLink(String link) {
        if (link.isBlank()) {
            return new ValidationResult(false, "Please enter a valid link");
        }
        if (!link.matches("^(https?://)?(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.(?!-)[A-Za-z0-9-]{1,63}(?<!-))*\\.[A-Za-z]{2,}(:\\d{1,5})?(/[^\\s]*)?$")) {
            return new ValidationResult(false, "Please enter a valid URL");
        }

        return new ValidationResult(true, "");
    }

    public static ValidationResult validateEmail(String email) {
        if (email.isBlank()) {
            return new ValidationResult(false, "Please enter a valid email");
        }
        if (!email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            return new ValidationResult(false, "Please enter a valid email");
        }

        return new ValidationResult(true, "");
    }
}

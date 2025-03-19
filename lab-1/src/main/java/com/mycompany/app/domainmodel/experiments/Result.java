package com.mycompany.app.domainmodel.experiments;

import java.util.Objects;

public record Result(String conclusion, boolean isImportant) {
    public Result {
        Objects.requireNonNull(conclusion, "Conclusion from experiment result cannot be null");
    }
}

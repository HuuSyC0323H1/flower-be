package com.flower.d2c.infrastructure.exception;

import java.util.ArrayList;
import java.util.List;

public class ResourceViolation extends ResourceException {
    private final List<ErrorDetailEntry> details = new ArrayList<>();

    public ResourceViolation(String message) {
        super(message);
    }

    public ResourceViolation(Throwable cause) {
        super(cause);
    }

    public ResourceViolation(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceViolation(Throwable cause, ErrorDetailEntry error) {
        super(cause);
        details.add(error);
    }

    public ResourceViolation(String message, ErrorDetailEntry error) {
        super(message);
        details.add(error);
    }

    public ResourceViolation(String message, Throwable cause, ErrorDetailEntry error) {
        super(message, cause);
        details.add(error);
    }

    public List<ErrorDetailEntry> getDetails() {
        return this.details;
    }
}

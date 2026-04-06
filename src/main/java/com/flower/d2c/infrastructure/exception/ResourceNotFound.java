package com.flower.d2c.infrastructure.exception;

import java.util.ArrayList;
import java.util.List;

public class ResourceNotFound extends ResourceException {
    private final List<ErrorDetailEntry> details = new ArrayList<>();

    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound(Throwable cause) {
        super(cause);
    }

    public ResourceNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFound(Throwable cause, ErrorDetailEntry error) {
        super(cause);
        details.add(error);
    }

    public ResourceNotFound(String message, ErrorDetailEntry error) {
        super(message);
        details.add(error);
    }

    public ResourceNotFound(String message, Throwable cause, ErrorDetailEntry error) {
        super(message, cause);
        details.add(error);
    }

    public List<ErrorDetailEntry> getDetails() {
        return this.details;
    }
}

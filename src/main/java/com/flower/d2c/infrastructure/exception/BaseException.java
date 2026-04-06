package com.flower.d2c.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private Object[] arrs;

    public BaseException(String message) {
        super(message, null, false, false);
    }

    public BaseException(String message, Object... arrs) {
        super(message, null, false, false);
        this.arrs = arrs;
    }

    public BaseException(String message, Throwable t) {
        super(message, t, false, false);
    }

    public BaseException(String message, Throwable t, Object... arrs) {
        super(message, t, false, false);
        this.arrs = arrs;
    }

}

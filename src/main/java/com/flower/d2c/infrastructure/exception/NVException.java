package com.flower.d2c.infrastructure.exception;

import com.flower.d2c.infrastructure.constans.ErrorCode;
import lombok.Getter;

@Getter
public class NVException extends BaseException {

    private final boolean resolvedMessage;

    public NVException(String message) {
        super(message);
        this.resolvedMessage = false;
    }

    public NVException(String message, boolean resolvedMessage) {
        super(message);
        this.resolvedMessage = resolvedMessage;
    }

    public NVException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.resolvedMessage = false;
    }

    public NVException(ErrorCode errorCode, Throwable t) {
        super(errorCode.toString(), t);
        this.resolvedMessage = false;
    }

    public NVException(ErrorCode errorCode, Throwable t, Object[] arrs) {
        super(errorCode.toString(), t, arrs);
        this.resolvedMessage = false;
    }

    public NVException(String message, Object[] arrs) {
        super(message, arrs);
        this.resolvedMessage = false;
    }

    public NVException(String message, Throwable t, Object[] arrs) {
        super(message, t, arrs);
        this.resolvedMessage = false;
    }
}


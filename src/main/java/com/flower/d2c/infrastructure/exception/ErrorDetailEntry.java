package com.flower.d2c.infrastructure.exception;

import java.io.Serializable;

public class ErrorDetailEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDetailEntry(String message) {
        this.message = message;
    }

}

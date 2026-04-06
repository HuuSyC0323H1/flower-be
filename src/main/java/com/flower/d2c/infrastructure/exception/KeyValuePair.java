package com.flower.d2c.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class KeyValuePair {
    private String field;
    private String message;
}

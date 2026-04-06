package com.flower.d2c.controller.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.flower.d2c.infrastructure.constans.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ResponseObject {
    private boolean isSuccess = false;
    private Object data;
    private String errorCode = ErrorCode.ERRORS.toString();
    private String message;
    private String requestId;
    private String status = "fail";

    public <T> ResponseObject(T obj) {
        processReponseObject(obj);
    }

    public void processReponseObject(Object obj) {
        if (obj != null) {
            this.isSuccess = true;
            this.errorCode = ErrorCode.SUCCESS.toString();
        }
        this.data = obj;
    }

    public ResponseObject(String errorCode, String message, boolean isSuccess) {
        this.errorCode = errorCode;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public ResponseObject(boolean isSuccess, Object data, String errorCode, String message, String requestId) {
        super();
        this.isSuccess = isSuccess;
        this.data = data;
        this.errorCode = errorCode;
        this.message = message;
        this.requestId = requestId;
    }

    public ResponseObject() {
        // super();
        // TODO Auto-generated constructor stub
    }
}

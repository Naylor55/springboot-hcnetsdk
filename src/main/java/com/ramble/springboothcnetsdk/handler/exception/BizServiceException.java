package com.ramble.springboothcnetsdk.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@EqualsAndHashCode(callSuper=false)
public class BizServiceException extends Exception {
    private String message;
    private Integer code=500001;
    public BizServiceException(String message) {
        super(message);
        this.message = message;
    }
    public BizServiceException(String message, Integer code) {
        super(message);
        this.message = message;
        this.code = code;
    }
    @Override
    public String getMessage() {
        return message;
    }
}

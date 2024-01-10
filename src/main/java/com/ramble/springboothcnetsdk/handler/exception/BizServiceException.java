package com.ramble.springboothcnetsdk.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Project     ngh-ptz-adapter
 * Package     com.nghsmart.nghptzadapter.handler.exception
 * Class       BizServiceException
 *
 * @author MingliangChen
 * Email       cnaylor@163.com
 * Description
 * @date 2023/7/7 9:38
 */

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

package com.ramble.springboothcnetsdk.handler;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Project     ngh-ptz-adapter
 * Package     com.nghsmart.nghptzadapter.handler
 * Class       GlobalResponseEntity
 *
 * @author MingliangChen
 * Email       cnaylor@163.com
 * Description
 * @date 2023/7/7 9:33
 */

@Data
@Accessors(chain = true)
public class GlobalResponseEntity<T> implements Serializable {

    private Boolean success = true;
    private String code = "000000";
    private String message = "request successfully";
    private T data;

    public GlobalResponseEntity() {
        super();
    }

    public GlobalResponseEntity(T data) {
        this.data = data;
    }

    public GlobalResponseEntity(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public GlobalResponseEntity(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public GlobalResponseEntity(Boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public GlobalResponseEntity(Boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static GlobalResponseEntity<?> badRequest(String code, String message) {
        return new GlobalResponseEntity<>(false, code, message);
    }

    public static GlobalResponseEntity<?> badRequest() {
        return new GlobalResponseEntity<>(false, "404000", "无法找到您请求的资源");
    }

}

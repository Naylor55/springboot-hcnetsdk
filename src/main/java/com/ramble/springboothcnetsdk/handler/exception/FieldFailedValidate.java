package com.ramble.springboothcnetsdk.handler.exception;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Project     ngh-ptz-adapter
 * Package     com.nghsmart.nghptzadapter.handler.exception
 * Class       FieldFailedValidate
 *
 * @author MingliangChen
 * Email       cnaylor@163.com
 * Description
 * @date 2023/7/7 9:41
 */

@Data
@Accessors(chain = true)
public class FieldFailedValidate implements Serializable {
    private String name;
    private String message;
}

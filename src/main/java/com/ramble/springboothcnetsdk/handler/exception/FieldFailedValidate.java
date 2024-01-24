package com.ramble.springboothcnetsdk.handler.exception;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;



@Data
@Accessors(chain = true)
public class FieldFailedValidate implements Serializable {
    private String name;
    private String message;
}

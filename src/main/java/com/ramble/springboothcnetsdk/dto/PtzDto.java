package com.ramble.springboothcnetsdk.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.dto
 * Class       PtzDto
 * date        2024/1/10 14:31
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description
 */

@ToString
@Data
public class PtzDto implements Serializable {
    private float p;

    private float t;

    private float z;

}


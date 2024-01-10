package com.ramble.springboothcnetsdk.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.dto
 * Class       LoginResultDto
 * date        2024/1/10 14:09
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description
 */
@Data

public class LoginResultDto implements Serializable {


    private String deviceInfo;

    private Integer loginHandler;
}
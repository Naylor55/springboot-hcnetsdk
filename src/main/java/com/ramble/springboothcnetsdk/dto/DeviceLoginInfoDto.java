package com.ramble.springboothcnetsdk.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.dto
 * Class       DeviceLoginInfoDto
 * date        2024/1/11 15:24
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description 设备登录信息实体
 */


@Data
@Accessors(chain = true)
public class DeviceLoginInfoDto implements Serializable {


    /**
     * 设备ip
     */
    private String ip;


    /**
     * 设备登录用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;
}

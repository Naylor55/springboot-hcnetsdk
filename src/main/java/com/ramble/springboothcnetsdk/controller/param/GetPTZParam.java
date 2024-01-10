package com.ramble.springboothcnetsdk.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.controller.param
 * Class       GetPTZParam
 * date        2024/1/10 14:31
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description
 */

@Data
public class GetPTZParam implements Serializable {

    /**
     * 相机ip
     */
    private String ip;

    /**
     * 相机登录账号
     */
    private String username;

    /**
     * 相机登录密码
     */
    private String password;

    /**
     * 通道编号， 默认为1。当鹰眼设备，一个ip对应两个实际设备（全景&球机）通过channelId区分
     */
    private Integer channelId = 1;

    /**
     * 相机品牌
     */
    private Integer cameraBrand;

    /**
     * 相机端口
     */
    private Integer port;
}

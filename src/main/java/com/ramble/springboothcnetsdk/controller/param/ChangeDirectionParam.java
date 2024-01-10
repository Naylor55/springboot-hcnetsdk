package com.ramble.springboothcnetsdk.controller.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.controller.param
 * Class       ChangeDirectionParam
 * date        2024/1/10 15:50
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ChangeDirectionParam extends BaseParam implements Serializable {

    private Integer command;

    private Integer action;

    private Integer speed;


}

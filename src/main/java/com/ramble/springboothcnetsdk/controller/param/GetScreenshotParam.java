package com.ramble.springboothcnetsdk.controller.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.controller.param
 * Class       GetScreenshotParam
 * date        2024/1/10 16:35
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class GetScreenshotParam extends BaseParam implements Serializable {
    private String filePath;
}

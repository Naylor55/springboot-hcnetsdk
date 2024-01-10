package com.ramble.springboothcnetsdk.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.dto
 * Class       MessageCallBackUserDataDto
 * date        2024/1/10 16:52
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description
 */


@Data
public class MessageCallBackUserDataDto implements Serializable {

    private LocalDateTime dateTime;

    private String desc;

}

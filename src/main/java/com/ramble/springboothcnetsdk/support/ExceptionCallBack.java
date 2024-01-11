package com.ramble.springboothcnetsdk.support;

import com.ramble.springboothcnetsdk.lib.HCNetSDK;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.support
 * Class       ExceptionCallBack
 * date        2024/1/11 14:10
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description 异常回调
 */


@Slf4j
public class ExceptionCallBack implements HCNetSDK.FExceptionCallBack {


    /**
     *
     * @param dwType 异常或重连等消息的类型
     * @param lUserID 登录ID
     * @param lHandle
     * @param pUser 透传用户自定义数据
     */
    @Override
    public void invoke(int dwType, int lUserID, int lHandle, Pointer pUser) {
        log.error("异常事件类型:" + dwType);
        log.error("登录id:" + lUserID);
    }
}

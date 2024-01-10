package com.ramble.springboothcnetsdk.support;

import com.ramble.springboothcnetsdk.lib.HCNetSDK;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.support
 * Class       MsgCallBackForv30
 * date        2024/1/10 16:45
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description  设备消息回调。V30、V31的接口设置报警回调函数是全局唯一的，注册多次会覆盖之前的，只有最后设置的回调函数有效，
 * 所有设备报警信息都是在同一个回调函数中返回的，通过报警设备信息（pAlarmInfo）区分是哪台设备；通过V50接口设置报警回调函数，
 * 支持设置多路不同的回调函数，最大支持16路，通过索引进行区分，所有的设备报警信息会同时在设置的各个回调函数里面返回，返回相同的数据，
 * 同样需要通过报警设备信息（pAlarmInfo）区分是哪台设备
 */

@Slf4j
public class MsgCallBackFor30 implements HCNetSDK.FMSGCallBack_V31 {


    /**
     * 报警回调函数有返回值，接收到数据需要返回TRUE
     * 如果接收到了还返回false，回一直回调？
     *
     * @param lCommand
     * @param pAlarmer
     * @param pAlarmInfo
     * @param dwBufLen
     * @param pUser      透传的用户自定义数据
     * @return
     */
    @Override
    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        log.info("进入MsgCallBackFor30");
        String userData = pUser.getString(0);
        log.info("透传的用户自定义数据：{}", userData);
        return true;
    }
}

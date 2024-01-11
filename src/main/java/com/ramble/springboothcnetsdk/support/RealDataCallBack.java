package com.ramble.springboothcnetsdk.support;

import com.ramble.springboothcnetsdk.lib.HCNetSDK;
import com.ramble.springboothcnetsdk.lib.PlayCtrl;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.support
 * Class       RealDataCallBack
 * date        2024/1/11 15:31
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description 预览回调
 */

@Component
public class RealDataCallBack implements HCNetSDK.FRealDataCallBack_V30 {
    static int count =0;

    @Autowired(required = false)
    private HikvisionSupport hikvisionSupport;

    @Override
    public void invoke(int lRealHandle, int dwDataType, Pointer pBuffer, int dwBufSize, Pointer pUser) {
        if (count == 100) {//降低打印频率
            System.out.println("码流数据回调...dwBufSize="+dwBufSize);
            count = 0;
        }
        count++;
        //播放库解码
        switch (dwDataType) {
            case HCNetSDK.NET_DVR_SYSHEAD: //系统头
                if (!hikvisionSupport.getPlayCtrl().PlayM4_GetPort(new IntByReference(-1))) //获取播放库未使用的通道号
                {
                    break;
                }
                if (dwBufSize > 0) {
                    if (!hikvisionSupport.getPlayCtrl().PlayM4_SetStreamOpenMode(new IntByReference(-1).getValue(), PlayCtrl.STREAME_REALTIME))  //设置实时流播放模式
                    {
                        break;
                    }
                    if (!hikvisionSupport.getPlayCtrl().PlayM4_OpenStream(new IntByReference(-1).getValue(), pBuffer, dwBufSize, 1024 * 1024)) //打开流接口
                    {
                        break;
                    }
                    if (!hikvisionSupport.getPlayCtrl().PlayM4_Play(new IntByReference(-1).getValue(), 1)) //播放开始
                    {
                        break;
                    }

                }
            case HCNetSDK.NET_DVR_STREAMDATA:   //码流数据
                if ((dwBufSize > 0) && (new IntByReference(-1).getValue() != -1)) {
                    if (!hikvisionSupport.getPlayCtrl().PlayM4_InputData(new IntByReference(-1).getValue(), pBuffer, dwBufSize))  //输入流数据
                    {
                        break;
                    }
                }
        }
    }
}

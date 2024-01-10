package com.ramble.springboothcnetsdk.support;

import com.alibaba.fastjson2.JSON;
import com.ramble.springboothcnetsdk.dto.LoginResultDto;
import com.ramble.springboothcnetsdk.dto.MessageCallBackUserDataDto;
import com.ramble.springboothcnetsdk.dto.PtzDto;
import com.ramble.springboothcnetsdk.handler.exception.BizServiceException;
import com.ramble.springboothcnetsdk.lib.HCNetSDK;
import com.ramble.springboothcnetsdk.util.OSUtils;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.support
 * Class       HikvisionSupport
 * <p>
 * Description 海康sdk支持类，封装对sdk的操作，例如：初始化，登录接口，各个业务接口等
 *
 * @date 2024/1/10 13:09
 */


@Slf4j
public class HikvisionSupport {
    private HCNetSDK hCNetSDK;

    /**
     * 构造函数中初始化SDK
     */
    public HikvisionSupport() {
        hCNetSDK = HCNetSDK.INSTANCE;
        log.info("开始初始化hikvision-Sdk");
        if (Objects.equals(OSUtils.getOsName(), "linux")) {
            log.info("InitSdk-is-linux");
            String userDir = System.getProperty("user.dir");
            log.info("InitSdk-userDir={}", userDir);
            String osPrefix = OSUtils.getOsPrefix();
            if (osPrefix.toLowerCase().startsWith("linux-i386")) {
                HCNetSDK.BYTE_ARRAY ptrByteArray1 = new HCNetSDK.BYTE_ARRAY(256);
                HCNetSDK.BYTE_ARRAY ptrByteArray2 = new HCNetSDK.BYTE_ARRAY(256);
                //这里是库的绝对路径，请根据实际情况修改，注意改路径必须有访问权限
                String strPath1 = System.getProperty("user.dir") + "/hkliblinux32/libcrypto.so.1.1";
                String strPath2 = System.getProperty("user.dir") + "/hkliblinux32/libssl.so.1.1";

                System.arraycopy(strPath1.getBytes(), 0, ptrByteArray1.byValue, 0, strPath1.length());
                ptrByteArray1.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(3, ptrByteArray1.getPointer());

                System.arraycopy(strPath2.getBytes(), 0, ptrByteArray2.byValue, 0, strPath2.length());
                ptrByteArray2.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(4, ptrByteArray2.getPointer());

                String strPathCom = System.getProperty("user.dir") + "/hkliblinux32/HCNetSDKCom/";
                HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
                System.arraycopy(strPathCom.getBytes(), 0, struComPath.sPath, 0, strPathCom.length());
                struComPath.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(2, struComPath.getPointer());
            } else if (osPrefix.toLowerCase().startsWith("linux-amd64")) {
                HCNetSDK.BYTE_ARRAY ptrByteArray1 = new HCNetSDK.BYTE_ARRAY(256);
                HCNetSDK.BYTE_ARRAY ptrByteArray2 = new HCNetSDK.BYTE_ARRAY(256);
                //这里是库的绝对路径，请根据实际情况修改，注意改路径必须有访问权限
                String strPath1 = System.getProperty("user.dir") + "/hkliblinux64/libcrypto.so.1.1";
                String strPath2 = System.getProperty("user.dir") + "/hkliblinux64/libssl.so.1.1";

                System.arraycopy(strPath1.getBytes(), 0, ptrByteArray1.byValue, 0, strPath1.length());
                ptrByteArray1.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(3, ptrByteArray1.getPointer());

                System.arraycopy(strPath2.getBytes(), 0, ptrByteArray2.byValue, 0, strPath2.length());
                ptrByteArray2.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(4, ptrByteArray2.getPointer());

                String strPathCom = System.getProperty("user.dir") + "/hkliblinux64/HCNetSDKCom/";
                HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
                System.arraycopy(strPathCom.getBytes(), 0, struComPath.sPath, 0, strPathCom.length());
                struComPath.write();
                hCNetSDK.NET_DVR_SetSDKInitCfg(2, struComPath.getPointer());
            } else {
                log.info("osPrefix={}", osPrefix);
            }
        }
        //初始化sdk
        boolean isOk = hCNetSDK.NET_DVR_Init();
        //注册报警信息回调
        HCNetSDK.FMSGCallBack_V31 callBackV31 = new MsgCallBackFor30();
        MessageCallBackUserDataDto userData = new MessageCallBackUserDataDto();
        userData.setDesc("用户自定义数据");
        userData.setDateTime(LocalDateTime.now());
        Memory m = new Memory(1024);
        m.setWideString(0, JSON.toJSONString(userData));
        hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(callBackV31, m);

        hCNetSDK.NET_DVR_SetConnectTime(10, 1);
        hCNetSDK.NET_DVR_SetReconnect(100, true);
        if (!isOk) {
            log.error("=================== InitHikvisionSDK init fail ===================");
        } else {
            log.info("============== InitHikvisionSDK init success ====================");
        }
    }



    /**
     * 登录
     *
     * @param m_sDeviceIP 设备ip
     * @param m_sUsername 设备用户名
     * @param m_sPassword 设备密码
     * @return 登录成功的唯一标识
     */
    public Integer login(String m_sDeviceIP, String m_sUsername, String m_sPassword) throws BizServiceException {
        log.info("login-param={}", m_sDeviceIP);
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息

        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());

        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());

        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());

        m_strLoginInfo.wPort = Short.valueOf("8000");
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.write();

        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息
        int loginHandler = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (loginHandler == -1) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            IntByReference errorInt = new IntByReference(errorCode);
            log.error("[HK] login fail errorCode:{}, errMsg:{}", errorCode, hCNetSDK.NET_DVR_GetErrorMsg(errorInt));
            throw new BizServiceException("海康sdk登录失败");
        } else {
            int iCharEncodeType = m_strDeviceInfo.byCharEncodeType;
            log.info("[HK] login success iCharEncodeType:{}", iCharEncodeType);
//            log.info("设备信息={}", JSON.toJSONString(m_strDeviceInfo));
            return loginHandler;
        }
    }

    /**
     * 获取设备信息
     *
     * @param m_sDeviceIP
     * @param m_sUsername
     * @param m_sPassword
     * @return
     */
    public LoginResultDto getDeviceInfo(String m_sDeviceIP, String m_sUsername, String m_sPassword) {
        log.info("LoginService-getDeviceInfo-param={}", m_sDeviceIP);
        LoginResultDto result = new LoginResultDto();

        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息

        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());

        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());

        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());

        m_strLoginInfo.wPort = Short.valueOf("8000");
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.write();

        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息
        int loginHandler = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (loginHandler == -1) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            IntByReference errorInt = new IntByReference(errorCode);
            log.error("[getDeviceInfo] login fail errorCode:{}, errMsg:{}", errorCode, hCNetSDK.NET_DVR_GetErrorMsg(errorInt));
            return null;
        } else {
            String deviceInfo = JSON.toJSONString(m_strDeviceInfo);
            log.info("设备信息={}", deviceInfo);
            result.setDeviceInfo(deviceInfo);
            result.setLoginHandler(loginHandler);
            loginOut(loginHandler);
            return result;
        }
    }

    /**
     * 退出
     *
     * @param loginHandler 登录成功的唯一标识
     * @return
     */
    public void loginOut(Integer loginHandler) {
        if (loginHandler == null) {
            log.error("[HK] logout null");
        }
        boolean b = hCNetSDK.NET_DVR_Logout(loginHandler);
        if (!b) {
            log.error("[HK] logout fail");
        } else {
            log.error("[HK] logout success");
        }
    }

    /**
     * 获取频道信息
     *
     * @param m_sDeviceIP
     * @param m_sUsername
     * @param m_sPassword
     * @return
     * @throws BizServiceException
     */
    public String getChannelInfo(String m_sDeviceIP, String m_sUsername, String m_sPassword) throws BizServiceException {
        String data = "";
        Integer loginHandler = login(m_sDeviceIP, m_sUsername, m_sPassword);
        HCNetSDK.NET_DVR_IPPARACFG_V40 outBuffer = new HCNetSDK.NET_DVR_IPPARACFG_V40();
        boolean result = hCNetSDK.NET_DVR_GetDVRConfig(loginHandler, HCNetSDK.NET_DVR_GET_IPPARACFG_V40, 0, outBuffer.getPointer(), outBuffer.size(), new IntByReference(1));
        if (result) {
            outBuffer.read();
            String response = JSON.toJSONString(outBuffer);
            log.info("getChannelInfo-response={}", response);
            data = response;
        } else {
            log.info("getChannelInfo-fail");
            int i = hCNetSDK.NET_DVR_GetLastError();
            IntByReference errorInt = new IntByReference(i);
            String msg = hCNetSDK.NET_DVR_GetErrorMsg(errorInt);
            log.info("lastErrorCode={} , msg={}", i, msg);
            throw new BizServiceException(i + "," + msg);
        }
        loginOut(loginHandler);
        return data;
    }

    /**
     * 获取ptz
     *
     * @param m_sDeviceIP
     * @param m_sUsername
     * @param m_sPassword
     * @param channelId   通道编号，默认1
     * @return
     */
    public PtzDto getPtz(String m_sDeviceIP, String m_sUsername, String m_sPassword, Integer channelId) throws BizServiceException {
        PtzDto ptzDto = new PtzDto();
        int loginHandler = login(m_sDeviceIP, m_sUsername, m_sPassword);
        HCNetSDK.NET_DVR_PTZPOS oldPtz = new HCNetSDK.NET_DVR_PTZPOS();
        boolean result = hCNetSDK.NET_DVR_GetDVRConfig(loginHandler, HCNetSDK.NET_DVR_GET_PTZPOS, Optional.ofNullable(channelId).orElse(1), oldPtz.getPointer(), oldPtz.size(), new IntByReference(1));
        if (result) {
            oldPtz.read();
            log.info("原始ptz,{} ; {} ; {}", oldPtz.wPanPos, oldPtz.wTiltPos, oldPtz.wZoomPos);
            ptzDto.setP(Integer.parseInt(Integer.toHexString(oldPtz.wPanPos).trim()) * 0.1f);
            ptzDto.setT((Integer.parseInt(Integer.toHexString(oldPtz.wTiltPos).trim())) * 0.1f);
            ptzDto.setZ((Integer.parseInt(Integer.toHexString(oldPtz.wZoomPos).trim())) * 0.1f);
        } else {
            int i = hCNetSDK.NET_DVR_GetLastError();
            IntByReference errorInt = new IntByReference(i);
            String msg = hCNetSDK.NET_DVR_GetErrorMsg(errorInt);
            log.error("getPtz-fail,errorCode={},errorMsg={}", i, msg);
        }
        log.info("getPtz-response={}", ptzDto.toString());
        hCNetSDK.NET_DVR_Logout(loginHandler);
        return ptzDto;
    }


    /**
     * 该表ptz
     *
     * @param m_sDeviceIP
     * @param m_sUsername
     * @param m_sPassword
     * @param p
     * @param t
     * @param z
     * @throws BizServiceException
     */
    public void changePtz(String m_sDeviceIP, String m_sUsername, String m_sPassword, float p, float t, float z) throws BizServiceException {
        int loginHandler = login(m_sDeviceIP, m_sUsername, m_sPassword);
        HCNetSDK.NET_DVR_PTZPOS param = new HCNetSDK.NET_DVR_PTZPOS();
        // wAction 操作类型，仅在设置时有效。1-定位PTZ参数，2-定位P参数，3-定位T参数，4-定位Z参数，5-定位PT参数
        param.wAction = 1;
        param.wPanPos = (short) (Integer.parseInt(String.valueOf((int) (p * 10)), 16));
        param.wTiltPos = (short) (Integer.parseInt(String.valueOf((int) (t * 10)), 16));
        param.wZoomPos = (short) (Integer.parseInt(String.valueOf((int) (z * 10)), 16));
        param.write();
        boolean setResult = hCNetSDK.NET_DVR_SetDVRConfig(loginHandler, HCNetSDK.NET_DVR_SET_PTZPOS, 1, param.getPointer(), param.size());
        if (!setResult) {
            int i = hCNetSDK.NET_DVR_GetLastError();
            IntByReference errorInt = new IntByReference(i);
            String msg = hCNetSDK.NET_DVR_GetErrorMsg(errorInt);
            log.debug("设置PTZ坐标信息失败，错误码：{}，{}", i, msg);
        }
        loginOut(loginHandler);
    }

    /**
     * 改变方向
     *
     * @param m_sDeviceIP
     * @param m_sUsername
     * @param m_sPassword
     * @param channelId   通道id
     * @param command     控制命令：21=云台上仰，24=云台右转，11=焦距变大，64=云台上仰和左转和焦距变大(倍率变大)
     * @param action      动作：0=开始，1=停止
     * @param speed       速度：1-7
     * @throws BizServiceException
     */
    public void changeDirection(String m_sDeviceIP, String m_sUsername, String m_sPassword, Integer channelId, int command, int action, int speed) throws BizServiceException {
        int loginHandler = login(m_sDeviceIP, m_sUsername, m_sPassword);
        boolean result = hCNetSDK.NET_DVR_PTZControlWithSpeed_Other(loginHandler, 1, command, action, speed);
        if (!result) {
            int i = hCNetSDK.NET_DVR_GetLastError();
            IntByReference errorInt = new IntByReference(i);
            String msg = hCNetSDK.NET_DVR_GetErrorMsg(errorInt);
            log.debug("changeDirection失败，错误码：{}，{}", i, msg);
        }
        loginOut(loginHandler);
    }

    /**
     * 获取屏幕快照
     *
     * @param m_sDeviceIP
     * @param m_sUsername
     * @param m_sPassword
     * @param filePath    文件存放路径，路径+文件名+文件后缀（jpeg）
     * @throws BizServiceException
     */
    public void getScreenshot(String m_sDeviceIP, String m_sUsername, String m_sPassword, String filePath) throws BizServiceException {
        int loginHandler = login(m_sDeviceIP, m_sUsername, m_sPassword);
        HCNetSDK.NET_DVR_JPEGPARA jpegParam = new HCNetSDK.NET_DVR_JPEGPARA();
        jpegParam.wPicQuality = 0;
//        String filePath = "D:\\temp\\1.jpeg";
        boolean result = hCNetSDK.NET_DVR_CaptureJPEGPicture(loginHandler, 1, jpegParam, filePath.getBytes());
        if (!result) {
            int i = hCNetSDK.NET_DVR_GetLastError();
            IntByReference errorInt = new IntByReference(i);
            String msg = hCNetSDK.NET_DVR_GetErrorMsg(errorInt);
            log.debug("changeDirection失败，错误码：{}，{}", i, msg);
        }
    }


}

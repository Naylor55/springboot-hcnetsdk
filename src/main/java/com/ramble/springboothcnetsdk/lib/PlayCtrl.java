package com.ramble.springboothcnetsdk.lib;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.lib
 * Class       PlayCtrl
 * date        2024/1/11 14:45
 * author      cml
 * Email       liangchen_beijing@163.com
 * Description 播放库声明，涉及适配预览，回放等都需要播放库的支持
 */

//播放库函数声明,PlayCtrl.dll
public interface PlayCtrl extends Library {
    public static final int STREAME_REALTIME = 0;
    public static final int STREAME_FILE = 1;

    boolean PlayM4_GetPort(IntByReference nPort);

    boolean PlayM4_OpenStream(int nPort, Pointer pFileHeadBuf, int nSize, int nBufPoolSize);

    boolean PlayM4_InputData(int nPort, Pointer pBuf, int nSize);

    boolean PlayM4_CloseStream(int nPort);

    boolean PlayM4_SetStreamOpenMode(int nPort, int nMode);

    //若出现 HCNetSDK.NET_DVR_PREVIEWINFO.HWND找不到引用，可做如下处理：1：在ProjectStructure中引入官方示例代码中的 examples.jar ；2：将 HWND 类型修改为 int
    boolean PlayM4_Play(int nPort, int hWnd);

    boolean PlayM4_Stop(int nPort);

    boolean PlayM4_SetSecretKey(int nPort, int lKeyType, String pSecretKey, int lKeyLen);

    boolean PlayM4_GetPictureSize(int nPort, IntByReference pWidth, IntByReference pHeight);

    boolean PlayM4_GetJPEG(int nPort, Pointer pBitmap, int nBufSize, IntByReference pBmpSize);

    int PlayM4_GetLastError(int nPort);

    boolean PlayM4_SetDecCallBackExMend(int nPort, DecCallBack decCBFun, Pointer pDest, int nDestSize, int nUser);

    public static interface DecCallBack extends Callback {
        void invoke(int nPort, Pointer pBuf, int nSize, FRAME_INFO pFrameInfo, int nReserved1, int nReserved2);
    }

    public class FRAME_INFO extends Structure {
        public int nWidth;                   /* 画面宽，单位像素。如果是音频数据，则为音频声道数 */
        public int nHeight;                     /* 画面高，单位像素。如果是音频数据，则为样位率 */
        public int nStamp;                           /* 时标信息，单位毫秒 */
        public int nType;                            /* 数据类型，T_AUDIO16, T_RGB32, T_YV12 */
        public int nFrameRate;                /* 编码时产生的图像帧率，如果是音频数据则为采样率 */
        public int dwFrameNum;                      /* 帧号 */
    }

}
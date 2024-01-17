# 概述

这是一个SpringBoot工程，集成了海康网络设备sdk的基础功能，可以作为集成or对接海康摄像头、NVR、视频服务器等的示例

# 前提

* 至少一台海康摄像头（球机、枪机、鹰眼），NVR。。。。。。
* 设备的ip地址，用户名，密码

# 功能

* 内嵌海康网络设备sdk v6.1
* 提供HCNetSDK接口，用于和Native代码（dll or so）进行交互
* 提供PlayCtrl接口，用于视频预览、回放、抓图功能
* 获取ptz
* 改变ptz
* 云台方向控制
* 获取屏幕快照，截图，预览截屏
* 报警信息/事件回调
* sdk全局异常回调
* 获取设备当前状态
* sdk日志级别控制，输出位置控制
* 设置连接超时时间和重连次数

# 特点

* 通过条件装配灵活初始化 sdk
* 可用于枪机、球机、鹰眼等
* 封装HikvisionSupport作为边缘层和SDK交互
* 适配的操作系统：win64，linux64，armLinux64
* 完整的程序日志和sdk日志记录
* 合理的工程目录接口和代码编排
* 完善的注释

# 如何使用

* git clone
* refresh gradle dependencies
* run or debug 


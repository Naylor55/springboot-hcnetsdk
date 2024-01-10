 # 概述
 这是一个SpringBoot工程，集成了海康网络设备sdk的基础功能，可以作为集成or对接海康摄像头、NVR、视频服务器等的示例
 

# 功能

* 内嵌海康网络设备sdk v6.1
* 提供HCNetSDK接口，用于和Native代码（dll or so）进行交互
* 获取ptz
* 改变ptz
* 云台方向控制
* 获取屏幕快照，截图，预览截屏
* 报警信息/事件回调


# 特点

* 通过条件装配灵活初始化 sdk
* 可用于枪机、球机、鹰眼等
* 封装HikvisionSupport作为边缘层和SDK交互
* 适配的操作系统：win64，linux64，armLinux64
* 合理的工程目录接口和代码编排
* 完善的注释

# 如何使用

* git clone 
* refresh gradle dependencies
* run  or  debug 


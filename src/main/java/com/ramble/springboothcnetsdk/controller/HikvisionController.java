package com.ramble.springboothcnetsdk.controller;

import com.alibaba.fastjson2.JSON;
import com.ramble.springboothcnetsdk.controller.param.*;
import com.ramble.springboothcnetsdk.dto.PtzDto;
import com.ramble.springboothcnetsdk.support.HikvisionSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.controller
 * Class       HikvisionController
 * <p>
 * Description
 *
 * @date 2024/1/10 13:08
 */

@Api(tags = "海康 - api")
@Slf4j
@RestController
@RequestMapping("/hikvision")
public class HikvisionController {

    @Autowired(required = false)
    private HikvisionSupport hikvisionSupport;

    /**
     * 改变 ptz
     *
     * @param param
     * @return
     * @throws InterruptedException
     */
    @ApiOperation("改变ptz")
    @PutMapping("/ptz")
    public Boolean changePtz(@RequestBody ChangePTZParam param) {
        try {
            hikvisionSupport.changePtz(param.getIp(), param.getUsername(), param.getPassword(), param.getP(), param.getT(), param.getZ());
            return true;
        } catch (Exception e) {
            log.error("changePtz-error,e={},stackTrace={}", e.getMessage(), e.getStackTrace());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 ptz
     *
     * @return
     */
    @ApiOperation("获取ptz")
    @PostMapping("/ptz")
    public PtzDto getPtz(@RequestBody GetPTZParam param) {
        try {
            PtzDto ptz = hikvisionSupport.getPtz(param.getIp(), param.getUsername(), param.getPassword(), null);
            return ptz;
        } catch (Exception e) {
            log.error("getPtz-error,e={},stackTrace={}", e.getMessage(), e.getStackTrace());
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation("云台方向控制")
    @PostMapping("/direction")
    public void changeDirection(@RequestBody ChangeDirectionParam param) {
        try {
            hikvisionSupport.changeDirection(param.getIp(), param.getUsername(), param.getPassword(), null, param.getCommand(), param.getAction(), param.getSpeed());
        } catch (Exception e) {
            log.error("getPtz-error,e={},stackTrace={}", e.getMessage(), e.getStackTrace());
            e.printStackTrace();
        }
    }

    @ApiOperation("获取屏幕快照")
    @PostMapping("/screenshot")
    public void getScreenshot(@RequestBody GetScreenshotParam param) {
        try {
            hikvisionSupport.getScreenshot(param.getIp(), param.getUsername(), param.getPassword(), param.getFilePath());
        } catch (Exception e) {
            log.error("getPtz-error,e={},stackTrace={}", e.getMessage(), e.getStackTrace());
            e.printStackTrace();
        }
    }

}

package com.ramble.springboothcnetsdk.config;

import com.ramble.springboothcnetsdk.condition.HikvisionSdkInitCondition;
import com.ramble.springboothcnetsdk.support.HikvisionSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.config
 * Class       SdkInitConfig
 *
 * @author cml
 * Email        liangchen_beijing@163.com
 * Description
 * @date 2024/1/10 13:27
 */

@Configuration
public class SdkInitConfig {

    /**
     * 初始化海康sdk。
     * 若满足Conditional注解，则向容器中注入 HikvisionSupport
     * @return
     */
    @Conditional(HikvisionSdkInitCondition.class)
    @Bean
    HikvisionSupport initHikvisionSdk() {
        return new HikvisionSupport();
    }
}

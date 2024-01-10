package com.ramble.springboothcnetsdk.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 * Project     springboot-hcnetsdk
 * Package     com.ramble.springboothcnetsdk.condition
 * Class       HikvisionSdkInitCondition
 *
 * @author cml
 * Email
 * Description   海康sdk初始化条件装配
 * @date 2024/1/10 13:19
 */
public class HikvisionSdkInitCondition implements Condition {


    /**
     * 装配规则，根据配置文件中hikvision.enable的值，为true或者1则装配
     *
     * @param context  the condition context
     * @param metadata the metadata of the {@link org.springframework.core.type.AnnotationMetadata class}
     *                 or {@link org.springframework.core.type.MethodMetadata method} being checked
     * @return 返回true，允许初始化；否则不允许
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String property = context.getEnvironment().getProperty("hikvision.enable");
        if (Objects.nonNull(property)) {
            return property.contains("true") || property.contains("1");
        } else {
            return false;
        }
    }
}

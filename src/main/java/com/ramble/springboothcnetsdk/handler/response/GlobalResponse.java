package com.ramble.springboothcnetsdk.handler.response;

import com.alibaba.fastjson2.JSON;
import com.ramble.springboothcnetsdk.handler.GlobalResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Project     ngh-ptz-adapter
 * Package     com.nghsmart.nghptzadapter.handler.response
 * Class       GlobalResponse
 *
 * @author MingliangChen
 * Email       cnaylor@163.com
 * Description
 * @date 2023/7/7 9:35
 */


@Slf4j
@RestControllerAdvice("com.ramble.springboothcnetsdk")
public class GlobalResponse implements ResponseBodyAdvice<Object> {

    /**
     * 拦截之前业务处理，请求先到supports再到beforeBodyWrite
     * 用法1：自定义是否拦截。若方法名称（或者其他维度的信息）在指定的常量范围之内，则不拦截。
     *
     * @param methodParameter
     * @param aClass
     * @return 返回true会执行拦截；返回false不执行拦截
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //TODO 过滤
        return true;
    }

    /**
     * 向客户端返回响应信息之前的业务逻辑处理
     * 用法1：对响应结果编排统一的数据结构
     * 用法2：在写入客户端响应之前统一加密
     *
     * @param responseObject     响应内容
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object responseObject, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //responseObject是否为null
        if (null == responseObject) {
            return new GlobalResponseEntity<>("55555", "response is empty.");
        }

        //responseObject是否是文件
        if (responseObject instanceof Resource) {
            return responseObject;
        }

        //该方法返回值类型是否是void
        //if ("void".equals(methodParameter.getParameterType().getName())) {
        //  return new GlobalResponseEntity<>("55555", "response is empty.");
        //}
        if (methodParameter.getMethod().getReturnType().isAssignableFrom(Void.TYPE)) {
            return new GlobalResponseEntity<>("55555", "response is empty.");
        }

        //该方法返回值类型是否是GlobalResponseEntity。若是直接返回，无需再包装一层
        if (responseObject instanceof GlobalResponseEntity) {
            return responseObject;
        }

        //处理string类型的返回值
        //当返回类型是String时，用的是StringHttpMessageConverter转换器，无法转换为Json格式
        //必须在方法体上标注 @PostMapping(value = "/test2", produces = "application/json; charset=UTF-8")
        if (responseObject instanceof String) {
            String responseString = JSON.toJSONString(new GlobalResponseEntity<>(responseObject));
            return responseString;
        }

        //该方法返回的媒体类型是否是application/json。若不是，直接返回响应内容
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return responseObject;
        }
        log.info("responseObject={}", JSON.toJSONString(responseObject));
        return new GlobalResponseEntity<>(responseObject);
    }
}


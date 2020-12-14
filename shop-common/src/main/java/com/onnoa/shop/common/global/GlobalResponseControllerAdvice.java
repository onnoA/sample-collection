package com.onnoa.shop.common.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onnoa.shop.common.exception.ServiceException;
import com.onnoa.shop.common.result.ResultBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description: 全局响应数据处理
 * @Author: onnoA
 * @Date: 2020/10/26 15:44
 */
@RestControllerAdvice(basePackages = {"com.onnoa.shop.demo.upload.controller"}) // 注意哦，这里要加上需要扫描的包
public class GlobalResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果接口返回的类型本身就是 ResultBean 那就没有必要进行额外的操作，返回false
        return !returnType.getParameterType().equals(ResultBean.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(ResultBean.success(data));
            } catch (JsonProcessingException e) {
                throw ServiceException.DATA_INVALID.format("返回String类型错误");
            }
        }
        // 原本返回 ResultBean 的直接返回
        if (data instanceof ResultBean) {
            return data;
        } else {
            // 将原本的数据包装在 ResultBean 里
            return ResultBean.success(data);
        }
    }
}

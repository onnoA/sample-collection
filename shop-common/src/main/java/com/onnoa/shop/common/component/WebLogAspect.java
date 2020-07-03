package com.onnoa.shop.common.component;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 日志切面对日志信息进行统一的处理
 * @Author: onnoA
 * @Date: 2020/5/20 15:56
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.onnoa.shop.*.*.controller.*.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("", joinPoint.getArgs());
        Long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        LOGGER.info("{}", map);
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        String methodDesc = "";
        // swagger 注解
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            methodDesc = log.value();
        }
        LOGGER.info("\n 请求开始 》》》》》》》》》》》 \n" +
                        "方法名:【{}】,路径:【{}】,请求方式:【{}】,请求参数【{}】,方法描述:【{}】 ",
                request.getRequestURI(), request.getRequestURL() + "",
                request.getMethod(), getParameter(method, joinPoint.getArgs()), methodDesc);
        Object result = joinPoint.proceed();
        LOGGER.info("\n请求结束:耗时： 》》》》》》》  {}ms , 响应结果: 》》》》》》》\n  {} ", (System.currentTimeMillis() - startTime), result);
        return result;
    }


    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        LOGGER.info("parameters{}", parameters);
        for (int i = 0; i < parameters.length; i++) {

            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
            // 将PathVariable注解修饰的参数作为请求参数
            PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
            if (pathVariable != null) {
                Map<String, Object> map = Maps.newHashMap();
                map.put(pathVariable.name(), pathVariable.value());
                argList.add(map);
            }
        }
        if (argList.isEmpty()) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

}

package com.onnoa.shop.demo.authority.system.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.onnoa.shop.common.constant.GlobalConstant;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.common.utils.ThreadLocalMap;
import com.onnoa.shop.common.utils.jwt.JWTConstant;
import com.onnoa.shop.common.utils.jwt.JWtObj;
import com.onnoa.shop.common.utils.jwt.JwtTokenUtils2;
import com.onnoa.shop.demo.authority.system.annotation.NoNeedTokenAuth;
import com.onnoa.shop.demo.authority.system.cache.AuthoritySystemCache;
import com.onnoa.shop.demo.authority.system.controller.SysUserController;
import com.onnoa.shop.demo.authority.system.dto.AuthDto;
import com.onnoa.shop.demo.authority.system.dto.RedisLoginUserDto;
import com.onnoa.shop.demo.authority.system.exception.UserException;

public class AccessTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private SysUserController sysUserController;

    private static Logger LOGGER = LoggerFactory.getLogger(AccessTokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        LOGGER.info("进入 preHandle 拦截器......");
        // 请求非controller接口，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 有不需要登录校验注解，直接放行
        if (isHavePermission(handler)) {
            return true;
        }

        // 校验token
        String accessToken = request.getHeader(JWTConstant.JWT_ACCESS_TOKEN_HEADER_KEY);
        String uid = request.getHeader(JWTConstant.UID);
        JWtObj jWtObj = JwtTokenUtils2.tranJWTObj(accessToken, JWTConstant.ACCESS_TOKEN_SECRET);
        RedisLoginUserDto redisLoginDto = (RedisLoginUserDto) AuthoritySystemCache.USER_ACCESS_TOKEN
            .get(jWtObj.getId());
        LOGGER.info("登录的用户:{}", JSONObject.toJSON(redisLoginDto));
        if (redisLoginDto == null || redisLoginDto.getRefTime() < new Date().getTime()) {
            throw UserException.ACCESS_TOKEN_HAS_EXPIRED;
        }
        if (!jWtObj.getId().equals(uid)) {
            throw UserException.USERID_AUTH_NOT_PASSED;
        }

        // 重新设置accessToken过期时间
        redisLoginDto.setRefTime(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        AuthoritySystemCache.USER_ACCESS_TOKEN.set(redisLoginDto.getId(), redisLoginDto, 24 * 60 * 60 * 1000);
        ThreadLocalMap.put(GlobalConstant.USER_TOKEN_AUTH_DTO, redisLoginDto);
        Boolean isSuccess = this.auth(redisLoginDto.getUsername(), request.getParameter("frontPath"),
            request.getParameter("interfaceUrl"));
        if (Boolean.FALSE.equals(isSuccess)) {
            throw UserException.USER_HAS_NOT_PERMISSION;
        }
        return isSuccess;
    }

    private Boolean auth(String username, String frontPath, String interfaceUrl) {
        AuthDto authDto = new AuthDto();
        authDto.setUsername(username);
        authDto.setFrontPath(frontPath);
        authDto.setInterfaceUrl(interfaceUrl);
        ResultBean result = sysUserController.auth(authDto);
        return (Boolean) result.getData();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {

    }

    private boolean isHavePermission(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return AnnotationUtils.findAnnotation(handlerMethod.getBean().getClass(), NoNeedTokenAuth.class) != null
            || AnnotationUtils.findAnnotation(handlerMethod.getMethod(), NoNeedTokenAuth.class) != null;
    }
}

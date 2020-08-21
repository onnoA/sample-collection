package com.onnoa.shop.demo.authority.system.controller;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.common.utils.HappyCaptchaUtils;
import com.onnoa.shop.demo.authority.system.annotation.NoNeedTokenAuth;
import com.onnoa.shop.demo.authority.system.dto.AuthDto;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.service.SysUserService;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;
import com.ramostear.captcha.HappyCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户相关接口", description = "提供用户相关的 Rest API，如登录、获取验证码等")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @PostMapping(value = "/login")
    @NoNeedTokenAuth
    @ApiOperation(value = "用户登录接口", notes = "登录接口")
    public ResultBean login(@RequestBody @Valid SysUserLoginDto loginDto) {
        String accessToken = userService.login(loginDto);
        return ResultBean.success(accessToken);
    }

    @GetMapping("/happyCaptcha")
    @ApiOperation(value = "登录获取验证码(happyCaptcha)接口 ", notes = "验证码接口")
    public void happyCaptcha(HttpServletRequest request, HttpServletResponse response) {
        // 二次刷新的时候都进行清除
        HappyCaptchaUtils captcha = new HappyCaptchaUtils();
        captcha.removeCaptcha(request);
        // 生成验证码
        captcha.HappyCaptchaUtils(request, response);

        // 设置过期时间（1分半）
        request.getSession().setMaxInactiveInterval(1 * 90);
        boolean verification = HappyCaptcha.verification(request, null, true);

        // HappyCaptcha.require(request, response).build().finish();
    }

    @GetMapping(value = "/verify")
    @NoNeedTokenAuth
    @ApiOperation(value = "登录获取验证码接口", notes = "验证码接口")
    public ResultBean getVerifyCode() {
        VerifyCodeVo resultVo = userService.getVerifyCode();
        return ResultBean.success(resultVo);
    }

    @PostMapping(value = "/auth")
    public ResultBean auth(@RequestBody @Valid AuthDto authDto) {
        Boolean hasAuth = userService.auth(authDto);
        return ResultBean.success(hasAuth);
    }
}

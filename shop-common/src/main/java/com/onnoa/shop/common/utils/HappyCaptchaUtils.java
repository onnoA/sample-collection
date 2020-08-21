package com.onnoa.shop.common.utils;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.common.Fonts;
import com.ramostear.captcha.support.CaptchaStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: HappyCaptchaUtil工具类
 * @Author: onnoA
 * @Date: 2020/7/30 09:15
 */
public class HappyCaptchaUtils {

    public void HappyCaptchaUtils(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request, response)
            // HappyCaptcha提供两种验证码展现形式：图片和动画。默认的展现形式为图片,可供选择的值有IMG和ANIM
            // 若展现形式为图片，则style(CaptchaStyle.IMG)可以省略。
            .style(CaptchaStyle.ANIM)
            // length()方法用于设置验证码字符长度，默认情况下缺省值为5
            .length(6)
            // width()方法可对验证码图片的宽度进行调节，默认的缺省值为160
            .width(180)
            // 同width()方法一样，height()方法用于设置验证码图片的高度，默认缺省值为50
            .height(60)
            // 默认缺省字体为微软雅黑 内置了四种字体
            .font(Fonts.getInstance().zhFont()).build().finish();
    }

    /**
     * 当验证码被使用后，你可以通过HappyCaptcha类种的remove()方法将Session中存放的验证码清理掉。
     * 手动清理Session中存放的验证码，HappyCaptcha验证码的Key为“happy-captcha”。 六、高级特性
     *
     * @param request
     */
    public void removeCaptcha(HttpServletRequest request) {
        HappyCaptcha.remove(request);
    }
}

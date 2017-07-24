package com.module.product.service;

import javax.servlet.http.HttpSession;

/**
 * Created by on 2017/6/6.
 */
public interface CaptchaService {
    String send(String mobile, int type);

    void check(String mobile, String captcha);

    void clean(String mobile);

    void setImageCaptcha(HttpSession session, String captcha);

    void checkImageCaptcha(HttpSession session, String captcha);

    void cleanImageCaptcha(HttpSession session);

    /**
     * 检查找回密码时存放的令牌是否正确，反正恶意调用/user/password/rest。
     * @param mobile
     * @return 检查通过返回true，否则false。
     */
    boolean checkFindPasswordToken(String mobile);

}


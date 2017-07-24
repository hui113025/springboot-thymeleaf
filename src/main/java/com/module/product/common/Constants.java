package com.module.product.common;

/**
 * Created by on 2016/6/24.
 */
public final class Constants {
    public static final String SESSION_USER_KEY = "SESSION_USER";
    public static final String SESSION_IMAGE_CAPTCHA_KEY = "IMAGE_CAPTCHA";
    public static final String CACHE_KEY_PREFIX_CAPTCHA_SMS = "captcha.sms.";
    public static final String FIND_PASSWORD_TOKEN_KEY = "user.find.password.";
    public static final String UNIQUE_LOGIN_REDIS_HASH_NAME = "user.unique.login";//登录唯一性
    public static final int SMS_TYPE_REG = 1;//验证码发送类型：注册
    public static final int SMS_TYPE_FIND_PASSWORD = 2;//验证码发送类型：找回密码
    public static final String USER_ACTIVE_SUBJECT = "user.active.subject";//用户激活的科目
    public static final String USER_MESSAGE_READ_TIME = "user.message.read.time";//用户激活的科目
    public static final String IS_VIP = "IS_VIP";//用户激活的科目
    public static String TI_USER_PAPER_ANSWER="ti_user_paper_answer";//拆表前缀
    public static final String videoConfigOption ="video.config.option";//选择视频（0:乐视 1:cc视频）
    public static String desKey="1be19ffafb9bd09611abc4c5ad21908d";
}

package com.module.product.service.impl;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.module.core.exception.ServiceException;
import com.module.product.orm.mapper.StudentMapper;
import com.module.product.orm.model.Student;
import com.module.product.service.CaptchaService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.module.product.common.Constants.*;


/**
 * Created by on 2016/12/19.
 */
@Service
public class CaptchaServiceImpl implements CaptchaService, InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private CCPRestSmsSDK sdk;

    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private StudentMapper studentMapper;

    public String send(String mobile, int type) {
        Student condition = new Student();
        condition.setMobile(mobile);
        Student user = studentMapper.selectOne(condition);
        if (type == SMS_TYPE_REG) {
            if (user != null) {
                throw new ServiceException("手机号已经被注册");
            }
        } else if (type == SMS_TYPE_FIND_PASSWORD) {
            if (user == null) {
                throw new ServiceException("手机号尚未注册");
            }
        }
        String captcha = RandomStringUtils.randomNumeric(6);
        return captcha;
    }

    public void check(String mobile, String captcha) {
        String key = CACHE_KEY_PREFIX_CAPTCHA_SMS + mobile;
        if (redis.hasKey(key)) {
            String _captcha = redis.opsForValue().get(key);
            if (!StringUtils.equals(captcha, _captcha)) {
                throw new ServiceException("验证码不正确");
            }
        } else {
            throw new ServiceException("验证码已失效");
        }

    }

    public void clean(String mobile) {
        String key = CACHE_KEY_PREFIX_CAPTCHA_SMS + mobile;
        if (redis.hasKey(key)) redis.delete(key);
    }

    public void setImageCaptcha(HttpSession session, String captcha) {
        session.setAttribute(SESSION_IMAGE_CAPTCHA_KEY, captcha);
    }

    public void checkImageCaptcha(HttpSession session, String captcha) {
        if (!StringUtils.equals(captcha, (String) session.getAttribute(SESSION_IMAGE_CAPTCHA_KEY))) {
            throw new ServiceException("图形验证码错误");
        }
    }

    public void cleanImageCaptcha(HttpSession session) {
        if (session.getAttribute(SESSION_IMAGE_CAPTCHA_KEY) != null) {
            session.removeAttribute(SESSION_IMAGE_CAPTCHA_KEY);
        }
    }

    @Override
    public boolean checkFindPasswordToken(String mobile) {
        boolean pass = false;
        String key = FIND_PASSWORD_TOKEN_KEY + mobile;
        if (redis.hasKey(key) && DigestUtils.md5Hex(mobile).equals(redis.opsForValue().get(key))) {
                redis.delete(key);
                pass = true;
        }
        return pass;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
       //初始化...
    }
}

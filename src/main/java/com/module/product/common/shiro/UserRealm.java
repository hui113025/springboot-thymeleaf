package com.module.product.common.shiro;

import com.module.product.orm.model.Student;
import com.module.product.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.io.Serializable;

import static com.module.product.common.Constants.SESSION_USER_KEY;
import static com.module.product.common.Constants.UNIQUE_LOGIN_REDIS_HASH_NAME;

/**
 * Created by on 2016/6/22.
 */
@Component
public class UserRealm extends AuthenticatingRealm {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private StudentService studentService;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Integer, Serializable> hashOperations;
    @Resource
    private SessionManager sessionManager;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String password = new String((char[]) token.getCredentials()); //得到密码
        String mobile = (String) token.getPrincipal();  //得到用户名

        Student student = studentService.findByMobile(mobile);

        //验证
        if (student == null || !StringUtils.equals(password, student.getPassword())) {
            throw new IncorrectCredentialsException();//用户名或密码错误异常
        }

        //判断该账号是否已在他出登录，如果是则踢出
        Integer uid = student.getId();
        if (hashOperations.hasKey(UNIQUE_LOGIN_REDIS_HASH_NAME, uid)) { //如果该账号已在他处登录,进行退出操作
            Serializable sessionId = (Serializable) hashOperations.get(UNIQUE_LOGIN_REDIS_HASH_NAME, uid);
            SessionKey key = new DefaultSessionKey(sessionId);
            try {
                Session session = sessionManager.getSession(key);
                if (session != null) {
                    session.stop();//结束会话
                }
            } catch (SessionException e) {
                logger.warn(e.getMessage(), e);
            }
        }

        //放入Session Scope
        Session session = SecurityUtils.getSubject().getSession();
        String nickName = mobile.substring(0, 3) + "****" + mobile.substring(7);
        nickName = StringUtil.isNotEmpty(student.getNickName()) ? student.getNickName() : nickName;
        student.setNickName(nickName);
        session.setAttribute(SESSION_USER_KEY, student);
        Serializable sid = session.getId();
        //记录登录
        hashOperations.put(UNIQUE_LOGIN_REDIS_HASH_NAME, uid, sid);

        //返回一个AuthenticationInfo实现
        return new SimpleAuthenticationInfo(mobile, password, getName());
    }

}
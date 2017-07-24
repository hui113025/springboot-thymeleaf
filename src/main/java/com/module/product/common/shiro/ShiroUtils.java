package com.module.product.common.shiro;


import com.module.product.common.Constants;
import com.module.product.orm.model.Student;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Shiro utils.
 *
 * lihengming
 */
public class ShiroUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShiroUtils.class);


    /**
     * Is login boolean.
     *
     * @return the boolean
     */
    public static boolean isLogin(){
        try {
            return SecurityUtils.getSubject().isAuthenticated() && null != ShiroUtils.getSession().getAttribute(Constants.SESSION_USER_KEY);
        }catch (UnknownSessionException e){
            return false;
        }

    }

    /**
     * Get session session.
     *
     * @return the session
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * Logout.
     */
    public static void logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("用户" + (String)subject.getPrincipal() + "退出登录");
            }
            subject.logout(); // session 会销毁
        }
    }

    /**
     * Get session user user stu.
     *
     * @return the user stu
     */
    public static Student getSessionUser(){
        return (Student) ShiroUtils.getSession().getAttribute(Constants.SESSION_USER_KEY);
     }

}



package com.module.product.common.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by on 2016/6/27.
 *
 * 登录唯一性（同一账号多客户端登录）异常
 */
public class UniqueLoginException extends AuthenticationException {
    public UniqueLoginException() {
    }

    public UniqueLoginException(String message) {
        super(message);
    }

    public UniqueLoginException(Throwable cause) {
        super(cause);
    }

    public UniqueLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.module.core.exception;

/**
 * Created by on 2017/2/8.
 */
public class RemoteServiceException extends ServiceException {
    public RemoteServiceException() {
    }

    public RemoteServiceException(String message) {
        super(message);
    }

    public RemoteServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

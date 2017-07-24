package com.module.core.common;

/**
 */
public enum ResultState {
    SUCCESS(200),
    FAIL(400),
    Unauthorized(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    //视频模块的状态
    VIDEO_SUCCESS(0),
    VIDEO_EXCEPTION(-1),
    INTERNAL_NOT_LECTURE_INFO(-2);

    public int state;

    private ResultState(int state) {
        this.state = state;
    }
}

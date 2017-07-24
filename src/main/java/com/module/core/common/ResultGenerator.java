package com.module.core.common;

/**
 *
 * Ruhang Cloud 服务调用响应结果生成工具
 */
public class ResultGenerator {
    public static Result genSuccessResult() {
        return new Result()
                .setState(ResultState.SUCCESS)
                .setStateInfo("SUCCESS")
                .setResInfo(new Object());
    }

    public static Result genSuccessResult(Object resInfo) {
        return new Result()
                .setState(ResultState.SUCCESS)
                .setStateInfo("SUCCESS")
                .setResInfo(resInfo);
    }

    public static Result genFailResult(String stateInfo) {
        return new Result()
                .setState(ResultState.FAIL)
                .setStateInfo(stateInfo);
    }
}

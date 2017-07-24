package com.module.core.common;

import com.alibaba.fastjson.JSON;

/**
 *
 * Ruhang Cloud 服务调用响应结果封装
 */
public class Result  {

    private int state;
    private String stateInfo;
    private Object resInfo;

    public Result setState(ResultState resultState) {
        this.state = resultState.state;
        return this;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public Result setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
        return this;
    }

    public Object getResInfo() {
        return resInfo;
    }

    public Result setResInfo(Object resInfo) {
        this.resInfo = resInfo;
        return this;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

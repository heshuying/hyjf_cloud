package com.hyjf.cs.user.bean;

/**
 * 简单接口返回封闭类
 * Created by cuigq on 2018/2/1.
 */
public class SimpleResultBean<T> extends WXBaseResultBean {

    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    //测评状态
    private String resultStatus; // 0:未测评  1：已测评

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}

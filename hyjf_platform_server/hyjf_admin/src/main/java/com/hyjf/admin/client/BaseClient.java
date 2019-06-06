package com.hyjf.admin.client;

/**
 * 统一client调用接口
 * @author zhangyk
 * @date 2018/7/11 11:37
 */
public interface BaseClient {


    <T> T postExe(String url, Object param, Class<T> clazz)  ;


    <T> T postExeNoException(String url, Object param, Class<T> clazz);

    <T> T getExe(String url, Class<T> clazz);

}

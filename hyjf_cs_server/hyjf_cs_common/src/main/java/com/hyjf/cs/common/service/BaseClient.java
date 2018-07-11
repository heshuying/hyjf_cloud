package com.hyjf.cs.common.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface BaseClient {


    public   <T> T postExe(String url,Object param, Class<T> clazz)  ;

}

package com.hyjf.cs.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class BaseClientImpl implements BaseClient {

    public static final Logger logger = LoggerFactory.getLogger(BaseClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    public static final  String SUCCESS_METHOD_NAME = "isSuccess";


    /**
     * POST调用原子层,并反射出调用结果
     * @author zhangyk
     * @date 2018/7/11 10:34
     */
    @Override
    public <T> T postExe(String url, Object param, Class<T> clazz) {
        try {
            T t = restTemplate.postForEntity(url, param, clazz).getBody();
            //logger.debug("调用原子层返回结果response = {}", JSON.toJSON(t));
            if (checkSuccess(t,clazz)){
                return t;
            }
            logger.error("调用原子层异常, 请求路径url : {} ", url);
            // 后期抛出自定义异常，使用拦截器定向拦截，向页面返回调用失败的类似错误信息
            throw new RuntimeException("调用原子层服务异常");
        }  catch (Exception e) {
            logger.error("调用原子层异常, 请求路径url : {} ", url);
            logger.error("调用原子层异常, errorStack : {} ", e);
            throw new RuntimeException("调用原子层服务异常");
        }
    }


    /**
     * GET调用
     * @author zhangyk
     * @date 2018/7/11 11:14
     */
    @Override
    public <T> T getExe(String url, Class<T> clazz) {
        try {
            T t = restTemplate.getForEntity(url,  clazz).getBody();
            //logger.debug("调用原子层返回结果response = {}", JSON.toJSON(t));
            if (checkSuccess(t,clazz)){
                return t;
            }
            logger.error("调用原子层异常, 请求路径url : {} ", url);
            throw new RuntimeException("调用原子层服务异常");
        }  catch (Exception e) {
            logger.error("调用原子层异常, 请求路径url : {} ", url);
            logger.error("调用原子层异常, errorStack : {} ", e);
            throw new RuntimeException("调用原子层服务异常");
        }
    }

    /**
     * 校验调用是否成功
     * @author zhangyk
     * @date 2018/7/11 11:25
     */
    private <T> Boolean checkSuccess(T t, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (t != null) {
            Object parent = clazz.getSuperclass().newInstance();
            Method isSuccess = clazz.getMethod(SUCCESS_METHOD_NAME, parent.getClass());
            if ((Boolean) isSuccess.invoke(parent, t)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}

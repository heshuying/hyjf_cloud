package com.hyjf.cs.common.service;

import com.alibaba.fastjson.JSON;
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


    /**
     * 调用原子层,并反射出调用结果
     * @author zhangyk
     * @date 2018/7/11 10:34
     */
    @Override
    public <T> T postExe(String url, Object param, Class<T> clazz) {
        try {
            T t = restTemplate.postForEntity(url, param, clazz).getBody();
            logger.info("调用原子层返回结果response = {}", JSON.toJSON(t));
            if (t != null) {
                Object parent = clazz.getSuperclass().newInstance();
                Method isSuccess = clazz.getMethod("isSuccess", parent.getClass());
                if ((Boolean) isSuccess.invoke(parent, t)) {
                    return t;
                }
            }
            logger.error("调用原子层异常, 请求路径url : {} ", url);
            throw new RuntimeException("调用原子层服务异常");
            // 多个catch 可以用Exception 统一代替
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("调用原子层异常, 请求路径url : {} ", url);
            logger.error("调用原子层异常, errorStack : {} ", e);
            throw new RuntimeException("调用原子层服务异常");
        }
        return null;
    }
}

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
            logger.info("调用原子层返回结果response = {}", JSON.toJSON(t));
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
            logger.info("调用原子层返回结果response = {}", JSON.toJSON(t));
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


//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//               佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？



}

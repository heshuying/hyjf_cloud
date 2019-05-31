/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.pay.lib.duiba.util;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.spring.SpringUtils;
import com.hyjf.pay.lib.config.URLSystemConfig;
import com.hyjf.pay.lib.duiba.bean.DuiBaCallBean;
import com.hyjf.pay.lib.duiba.bean.DuiBaCallResultBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangjun
 * @version DuiBaCallUtils, v0.1 2019/5/30 10:00
 */
public class DuiBaCallUtils {
    private static Logger logger = LoggerFactory.getLogger(DuiBaCallUtils.class);

    private static URLSystemConfig URLSystemConfig = SpringUtils.getBean(URLSystemConfig.class);

    private static RestTemplate restTemplate = SpringUtils.getBean(RestTemplate.class);

    private static final String REQUEST_DUIBA= "/call";

    public static DuiBaCallResultBean duiBaCall(DuiBaCallBean bean){
        DuiBaCallResultBean resultBean = null;
        // 请求类为空时直接返回
        if(null == bean){
            logger.error("内部服务调用兑吧接口请求类为空");
            return resultBean;
        }
        // 校验参数，必要参数为空时直接返回
        if(StringUtils.isBlank(bean.getOrderNum()) || StringUtils.isBlank(bean.getMsgType())){
            logger.error("内部服务调用兑吧接口请求参数不合法");
            return resultBean;
        }
        logger.info("内部服务调用兑吧接口开始：类型:{},兑吧订单号:{}", bean.getMsgType(), bean.getOrderNum());
        // 拼接url并请求
        String url = URLSystemConfig.getDuiBaUrl() + REQUEST_DUIBA;
        try {
            String result = restTemplate.postForEntity(url, bean, String.class).getBody();
            if(StringUtils.isNotBlank(result)){
                resultBean = JSONObject.parseObject(result, DuiBaCallResultBean.class);
            }
        } catch (Exception e){
            logger.error("内部服务调用兑吧接口异常:", e);
        }
        return resultBean;
    }
}

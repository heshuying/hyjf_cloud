package com.hyjf.pay.lib.fadada.util;

import java.util.Map;
import java.util.TreeMap;

import com.hyjf.common.spring.SpringUtils;
import com.hyjf.pay.lib.config.FddSystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import org.springframework.web.client.RestTemplate;

public class DzqzCallUtil {

   private static Logger log = LoggerFactory.getLogger(DzqzCallUtil.class);

    private static FddSystemConfig fddSystemConfig = SpringUtils.getBean(FddSystemConfig.class);
    private static RestTemplate restTemplate = SpringUtils.getBean(RestTemplate.class);

    public static DzqzCallBean callApiBg(DzqzCallBean bean){
        DzqzCallBean ret = null;
        try {
            // 取出调用pay工程的url
            bean.setAllParams(new TreeMap<String, String>());
            Integer userId = bean.getUserId();
            if (userId == null){
                throw new RuntimeException("userid不得为空！");
            }
            String orderId = GetOrderIdUtils.getOrderId2(userId);
            String txDate = GetOrderIdUtils.getTxDate();
            String txTime = GetOrderIdUtils.getTxTime();
            bean.setTxDate(txDate);
            bean.setTxTime(txTime);
            bean.setLogordid(orderId);
            bean.convert();

            String payUrl = fddSystemConfig.getFddUrl();
            if (Validator.isNull(payUrl)) {
                throw new Exception("接口工程URL不能为空");
            }
            Map<String, String> allParams = bean.getAllParams();

            // 调用法大大接口
            String result = restTemplate.postForEntity(payUrl, allParams, String.class).getBody();

            if (Validator.isNotNull(result)) {
                // 将返回字符串转换成DzqzCallBean
                ret = JSONObject.parseObject(result, DzqzCallBean.class);
            }
        } catch (Exception e) {
            log.info("------------------调用法大大接口失败,失败原因：" + e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("------------------调用法大大接口结束！txCode:" + bean.getTxCode());
        }
        return ret;
    }
}

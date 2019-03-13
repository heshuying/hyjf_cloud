package com.hyjf.cs.trade.controller.wechat.tender;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.trade.service.hjh.impl.HjhTenderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pangchengchao
 * @version CronTest, v0.1 2019/3/12 17:24
 */
public class CronTest {
    private static final Logger logger = LoggerFactory.getLogger(CronTest.class);
    public static void main(String[] args) {
        borrowCouponInvest();
        //planCouponInvest();
    }

    private static void planCouponInvest() {
        String  accountStr="";
        String  borrowNid="";
        String  platform="";
        String  couponGrantId="";
        String  ip="";
        String  ordId="";
        String  userId="";
        String  account="";
        String mainTenderNid="";
        // 开始使用优惠券
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        // 真实出借金额
        params.put("money", accountStr);
        // 借款项目编号
        params.put("borrowNid", borrowNid);
        // 平台
        params.put("platform", platform);
        // 优惠券id
        params.put("couponGrantId", couponGrantId);
        // ip
        params.put("ip", ip);
        // 真实出借订单号
        params.put("ordId", ordId);
        // 用户编号
        params.put("userId", userId);
        params.put("account", account);
        params.put("mainTenderNid", mainTenderNid);
        logger.info("加入计划 开始调用优惠券投资：{} ",JSONObject.toJSONString(params));

    }

    private static void borrowCouponInvest() {
        String money="";
        String borrowNid="";
        String platform="";
        String couponGrantId="";
        String ip="";
        String ordId="";
        String userId="";
        String userName="";

        // 开始使用优惠券
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        // 真实出借金额
        params.put("money", money);
        // 借款项目编号
        params.put("borrowNid", borrowNid);
        // 平台
        params.put("platform", platform);
        // 优惠券id
        params.put("couponGrantId", couponGrantId);
        // ip
        params.put("ip", ip);
        // 真实出借订单号
        params.put("ordId", ordId);
        // 用户编号
        params.put("userId", userId+"");
        params.put("userName", userName);
        logger.info("散标投资 开始调用优惠券投资：{} ",JSONObject.toJSONString(params));
    }
}

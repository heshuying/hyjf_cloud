/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh.hjhcheck;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.service.front.hjh.HjhPlanService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.chatbot.dd.DDChatbot;
import com.hyjf.common.chatbot.dd.DDChatbotBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * 汇计划各计划开放额度校验预警
 * @author liubin
 * @version HjhAlarmController, v0.1 2018/8/14 14:45
 */
@RestController
@RequestMapping(value = "/am-trade/hjhAlarmController")
public class HjhAlarmController extends BaseController {
    @Autowired
    private HjhPlanService hjhPlanService;
    @Autowired
    private SystemConfig systemConfig;

    /**
     * 汇计划各计划开放额度校验预警
     *
     * @return
     */
    @GetMapping("/batch/hjhOpenAccountCheck")
    private boolean hjhOpenAccountCheck() {
            logger.info("汇计划各计划开放额度校验预警任务 开始... ");
            // 非线上环境时，不执行该任务
            if(systemConfig.isEnvTest()){
                return true;
            }
            try {
                // 取得整个汇计划列表
                List<HjhPlan> hjhPlanList = this.hjhPlanService.selectHjhPlanList();
                logger.info("校验汇计划计划个数：" + hjhPlanList.size());
                // 校验每个计划的开放额度，输出结果
                for (HjhPlan hjhPlan : hjhPlanList) {
                    // 取得校验的计划编号
                    String planNid = hjhPlan.getPlanNid();
                    // 取得redis的开放额度
                    String accountRedis = RedisUtils.get(RedisConstants.HJH_PLAN + planNid);
                    // 取得db的开放额度
                    BigDecimal accountDB = hjhPlan.getAvailableInvestAccount();
                    // 校验开放额度是否一致
                    if (StringUtils.isEmpty(accountRedis) || accountDB == null || accountDB.compareTo(new BigDecimal(accountRedis)) != 0) {
                        // 不一致
                        RedisUtils.incr(RedisConstants.CONT_WARN_OF_HJH_ACCOUNT + planNid);
                        logger.warn("计划【" + planNid + "】的Redis和DB的开放额度不同(连续次数:" + RedisUtils.get(RedisConstants.CONT_WARN_OF_HJH_ACCOUNT + planNid) + "),"
                                + " Redis:" + accountRedis + ",  DB:" + accountDB + " ！");
                    } else {
                        // 一致
                        // 初期化
                        RedisUtils.set(RedisConstants.CONT_WARN_OF_HJH_ACCOUNT + planNid, "0");
                        logger.info("计划【" + planNid + "】的Redis和db的开放额度一致,"
                                + " Redis:" + accountRedis + ",  DB:" + accountDB + "。");
                    }
                    // 划连续开放额度不同次数 每5次时，非同步问题导致的不一致，开始预警
                    if (Integer.parseInt(RedisUtils.get(RedisConstants.CONT_WARN_OF_HJH_ACCOUNT + planNid)) > 0
                            && Integer.parseInt(RedisUtils.get(RedisConstants.CONT_WARN_OF_HJH_ACCOUNT + planNid)) % 5 == 0) {
                        // 消息内容
                        String content = "计划【" + planNid + "】的Redis和DB的开放额度不同(连续次数:" + RedisUtils.get(RedisConstants.CONT_WARN_OF_HJH_ACCOUNT + planNid) + "),"
                                + " Redis:" + accountRedis + ",  DB:" + accountDB + " ！！！";
                        // 输出日志
                        logger.error(content);
                        // 输出到钉钉群
                        DDChatbotBean chatbotBean = new DDChatbotBean(content);
                        DDChatbot.chatbotSend(DDChatbot.JAVA_PRODUCTION_ERR, JSON.toJSONString(chatbotBean));
                    }
                }
            } catch (Exception e) {
                logger.error("汇计划各计划开放额度校验预警任务 异常！！！ ");
                e.printStackTrace();
            }
            logger.info("汇计划各计划开放额度校验预警任务 结束... ");
        return true;
    }

}

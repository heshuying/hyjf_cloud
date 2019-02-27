/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh.hjhcheck;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.borrow.BorrowService;
import com.hyjf.am.trade.service.front.hjh.HjhAccedeService;
import com.hyjf.am.trade.service.front.hjh.HjhPlanService;
import com.hyjf.am.trade.service.front.hjh.HjhRepayService;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.chatbot.dd.DDChatbot;
import com.hyjf.common.chatbot.dd.DDChatbotBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * 汇计划各计划开放额度校验预警
 *
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
    @Autowired
    private HjhAccedeService hjhAccedeService;
    @Autowired
    private HjhRepayService hjhRepayService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * 汇计划各计划开放额度校验预警
     *
     * @return
     */
    @GetMapping("/batch/hjhOpenAccountCheck")
    private BooleanResponse hjhOpenAccountCheck() {
        logger.info("汇计划各计划开放额度校验预警任务 开始... ");
        // 非线上环境时，不执行该任务
        if (systemConfig.isEnvTest()) {
            return new BooleanResponse(true);
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
        return new BooleanResponse(true);
    }

    /**
     * hjh订单匹配期超过两天短信预警
     *
     * @author zhangyk
     * @date 2018/8/15 14:03
     */
    @GetMapping("/batch/hjhOrderMatchPeriodCheck")
    private Boolean hjhOrderMatchPeriodCheck() {
        List<HjhAccede> accedeList = hjhAccedeService.getPlanMatchPeriodList();
        if (CollectionUtils.isNotEmpty(accedeList)) {
            try {
                logger.info("获取到的size = " + accedeList.size());
                StringBuffer msg = new StringBuffer();
                String title = "计划订单匹配期>=2消息通知";
                for (int i = 0; i < accedeList.size(); i++) {
                    String orderId = accedeList.get(i).getAccedeOrderId();
                    msg.append("'");
                    msg.append(orderId);
                    msg.append("',");
                    if (i % 3 == 0) {
                        msg.append("\r\n");
                    }
                }
                msg.append("该计划订单匹配期已大于两天，请相关人员至后台查看并及时处理，谢谢");
                Boolean env_test = systemConfig.isEnvTest();
                logger.info("订单进入匹配期时间超过2天 evn_test is test ? " + env_test);
                String emailList = "";
                if (env_test) {
                    emailList = systemConfig.getHyjfAlertEmailTest();
                } else {
                    emailList = systemConfig.getHyjfAlertEmail();
                }
                String[] toMail = emailList.split(",");
                MailMessage message = new MailMessage(null, null, title, msg.toString(), null, toMail,
                        null,
                        MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), message));
                logger.info("邮件发送完成");
            } catch (Exception e) {
                logger.error("计划订单匹配期>=2 邮件预警发送异常");
                return false;
            }
        } else {
            logger.info("计划订单匹配期>=2 目标数据为空,不发送预警");
        }
        return true;
    }


    /**
     * 订单退出超过两天邮件预警
     *
     * @author zhangyk
     * @date 2018/8/15 15:44
     */
    @GetMapping("/batch/hjhOrderExitCheck")
    private Boolean hjhOrderExitCheck() {
        List<HjhRepay> list = hjhRepayService.getPlanExitCheck();
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                String title = "计划退出中时间>=2消息通知";
                StringBuffer msg = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    String orderId = list.get(i).getAccedeOrderId();
                    msg.append("'");
                    msg.append(orderId);
                    msg.append("'");
                    if (i % 3 == 0) {
                        msg.append("\r\n");
                    }
                }
                msg.append("该计划订单退出中已大于两天，请相关人员至后台查看并及时处理，谢谢");
                Boolean env_test = systemConfig.isEnvTest();
                logger.info("计划退出中时间>=2 evn_test is test ? " + env_test);
                String emailList = "";
                if (env_test) {
                    emailList = systemConfig.getHyjfAlertEmailTest();
                } else {
                    emailList = systemConfig.getHyjfAlertEmail();
                }
                String[] toMail = emailList.split(",");
                MailMessage message = new MailMessage(null, null, title, msg.toString(), null, toMail,
                        null,
                        MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), message));
                logger.info("邮件发送完成");
                return true;
            } catch (Exception e) {
                logger.error("计划退出中时间>=2 邮件预警发送异常 ");
                return false;
            }
        } else {
            logger.info("计划订单退出期>=2 目标数据为空,不发送预警");
        }
        return true;
    }


    /**
     * 订单出借异常短信预警
     *
     * @author zhangyk
     * @date 2018/8/15 16:23
     */
    @GetMapping("/batch/hjhOrderInvestExceptionCheck")
    private BooleanResponse hjhOrderInvestExceptionCheck() {
        BooleanResponse response = new BooleanResponse();
        List<HjhAccede> accedeList = hjhAccedeService.getPlanOrderInvestExceptionList();
        if (accedeList != null && accedeList.size() > 0) {
            try {
                // 发送邮件通知
                String title = "计划订单出借异常消息通知";
                StringBuffer msg = new StringBuffer();
                msg.append("计划订单出借异常，请至网站后台“异常出借-汇计化自动出借异常”查看并及时处理，谢谢");

                Boolean env_test = systemConfig.isEnvTest();
                logger.info("计划订单出借异常 evn_test is test ? " + env_test);
                String emailList = "";
                if (env_test) {
                    emailList = systemConfig.getHyjfAlertEmailTest();
                } else {
                    emailList = systemConfig.getHyjfAlertEmail();
                }
                String[] toMail = emailList.split(",");
                // 发送邮件
                MailMessage message = new MailMessage(null, null, title, msg.toString(), null, toMail,
                        null,
                        MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), message));
                SmsMessage smsMessage = new SmsMessage(null, null, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null,
                        CustomConstants.JYTZ_PLAN_ORDER_EXCECEPTION, CustomConstants.CHANNEL_TYPE_NORMAL);
                commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                logger.info("短信发送成功");
            } catch (Exception e) {
                logger.error("订单出借异常预警发送异常 ");
                response.setResultBoolean(false);
                return response;
            }
        } else {
            logger.info("计划订单出借异常size为空, 不发送预警");
        }
        response.setResultBoolean(true);
        return response;
    }

    /**
     * 清算日前一天，扫描处于复审中或者出借中的原始标的进行预警
     *
     * @author zhangyk
     * @date 2018/8/20 15:53
     */
    @GetMapping("/batch/alermBeforeLiquidateCheck")
    private Boolean AlermBeforeLiquidateCheck() {
        List<BorrowCustomizeVO> list = borrowService.selectUnDealBorrowBeforeLiquidate();
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                Boolean env_test = systemConfig.isEnvTest();
                String emailList = "";
                if (env_test) {
                    emailList = systemConfig.getHyjfAlertEmailTest();
                } else {
                    emailList = systemConfig.getHyjfAlertEmail();
                }
                String[] toMail = emailList.split(",");
                String title = "原始标的风险状态消息通知";
                StringBuffer msg = new StringBuffer();
                StringBuffer targetId = new StringBuffer();
                msg.append("明天即将进入清算日，部分原始标的仍然处于出借或复审中，请相关人员至后台查看并及时处理，谢谢 \n");
                for (BorrowCustomizeVO borrow : list) {
                    targetId.append(" " + borrow.getBorrowNid());
                }
                msg.append("目标id如下：" + targetId);
                MailMessage message = new MailMessage(null, null, title, msg.toString(), null, toMail,
                        null,
                        MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), message));
            } catch (Exception e) {
                logger.error("扫描处于复审中或者出借中的原始标的进行预警发送异常 ");
                return false;
            }
        } else {
            logger.info(">>>>清算前扫描原始标的查询目标数目为零，不发送邮件预警<<<<");
        }
        return true;
    }


}

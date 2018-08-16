/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh.hjhcheck;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.MailProducer;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.admin.hjhplan.HjhRepayService;
import com.hyjf.am.trade.service.front.hjh.HjhAccedeService;
import com.hyjf.am.trade.service.front.hjh.HjhPlanService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.chatbot.dd.DDChatbot;
import com.hyjf.common.chatbot.dd.DDChatbotBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private HjhAccedeService hjhAccedeService;
    @Autowired
    private HjhRepayService hjhRepayService;
    @Autowired
    private MailProducer mailProducer;
    @Autowired
    private SmsProducer smsProducer;

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

    /**
     * hjh订单匹配期超过两天短信预警
     * @author zhangyk
     * @date 2018/8/15 14:03
     */
    @GetMapping("/batch/hjhOrderMatchPeriodCheck")
    private Boolean hjhOrderMatchPeriodCheck() {
        List<HjhAccede> accedeList = hjhAccedeService.getPlanMatchPeriodList();
        if (CollectionUtils.isNotEmpty(accedeList)) {
           try {
               logger.info("获取到的size = "+ accedeList.size());
               StringBuffer msg = new StringBuffer();
               String title = "计划订单匹配期>=2消息通知";
               for (int i=0;i<accedeList.size();i++) {
                   String orderId = accedeList.get(i).getAccedeOrderId();
                   msg.append("'");
                   msg.append(orderId);
                   msg.append("',");
                   if (i % 3 == 0){
                       msg.append("\r\n");
                   }
               }
               msg.append("该计划订单匹配期已大于两天，请相关人员至后台查看并及时处理，谢谢");
               Boolean env_test = systemConfig.isEnvTest();
               logger.info("订单进入匹配期时间超过2天 evn_test is test ? " + env_test);
               String emailList= "";
               if (env_test){
                   emailList = systemConfig.getHyjfAlertEmailTest();
               }else{
                   emailList = systemConfig.getHyjfAlertEmail();
               }
               String [] toMail = emailList.split(",");
               MailMessage message = new MailMessage(null, null, title, msg.toString(),null,toMail,
                       null,
                       MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
               mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(message)));
               logger.info("邮件发送完成");
           }catch (Exception e){
               logger.error("计划订单匹配期>=2 邮件预警发送异常");
               return false;
           }
        }else{
            logger.info("计划订单匹配期>=2 目标数据为空,不发送预警");
        }
        return true;
    }



    /**
     * 订单退出超过两天邮件预警
     * @author zhangyk
     * @date 2018/8/15 15:44
     */
    @GetMapping("/batch/hjhOrderExitCheck")
    private Boolean hjhOrderExitCheck() {
        List<HjhRepay> list = hjhRepayService.getPlanExitCheck();
        if (CollectionUtils.isNotEmpty(list)){
            try {
                String title = "计划退出中时间>=2消息通知";
                StringBuffer msg = new StringBuffer();
                for (int i=0;i<list.size();i++) {
                    String orderId = list.get(i).getAccedeOrderId();
                    msg.append("'");
                    msg.append(orderId);
                    msg.append("'");
                    if (i % 3 == 0){
                        msg.append("\r\n");
                    }
                }
                msg.append("该计划订单退出中已大于两天，请相关人员至后台查看并及时处理，谢谢");
                Boolean env_test = systemConfig.isEnvTest();
                logger.info("计划退出中时间>=2 evn_test is test ? " + env_test);
                String emailList= "";
                if (env_test){
                    emailList = systemConfig.getHyjfAlertEmailTest();
                }else{
                    emailList = systemConfig.getHyjfAlertEmail();
                }
                String [] toMail = emailList.split(",");
                MailMessage message = new MailMessage(null, null, title, msg.toString(),null,toMail,
                        null,
                        MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(message)));
                logger.info("邮件发送完成");
                return true;
            }catch (Exception e){
                logger.error("计划退出中时间>=2 邮件预警发送异常 ");
                return false;
            }
        }else {
            logger.info("计划订单退出期>=2 目标数据为空,不发送预警");
        }
        return true;
    }




    /**
     * 订单投资异常短信预警
     * @author zhangyk
     * @date 2018/8/15 16:23
     */
    @GetMapping("/batch/hjhOrderInvestExceptionCheck")
    private Boolean hjhOrderInvestExceptionCheck() {
       List<HjhAccede> accedeList = hjhAccedeService.getPlanOrderInvestExceptionList();
        if (accedeList != null && accedeList.size() > 0) {
            try {
                // 发送邮件通知
                String title = "计划订单投资异常消息通知";
                StringBuffer msg = new StringBuffer();
                msg.append("计划订单投资异常，请至网站后台“异常投资-汇计化自动投资异常”查看并及时处理，谢谢");

                Boolean env_test = systemConfig.isEnvTest();
                logger.info("计划订单投资异常 evn_test is test ? " + env_test);
                String emailList= "";
                if (env_test){
                    emailList = systemConfig.getHyjfAlertEmailTest();
                }else{
                    emailList = systemConfig.getHyjfAlertEmail();
                }
                String [] toMail = emailList.split(",");
                // 发送邮件
                MailMessage message = new MailMessage(null, null, title, msg.toString(),null,toMail,
                        null,
                        MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(message)));
                // TODO: 2018/8/15   发送短信通知待完善
               /* SmsMessage smsMessage = new SmsMessage(null, null, null, null, MessageDefine.SMSSENDFORMANAGER, null,
                        CustomConstants.JYTZ_PLAN_ORDER_EXCECEPTION, CustomConstants.CHANNEL_TYPE_NORMAL);
                smsProcesser.gather(smsMessage);*/
                logger.info("短信发送成功");
            }catch (Exception e){
                logger.error("订单投资异常预警发送异常 ");
                return  false;
            }
        }else {
            logger.info("计划订单投资异常size为空, 不发送预警");
        }
        return true;
    }



}

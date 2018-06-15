/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.rtbbatch.BorrowApicronVo;
import com.hyjf.am.vo.rtbbatch.BorrowWithBLOBsVo;
import com.hyjf.am.vo.rtbbatch.IncreaseInterestInvestVo;
import com.hyjf.am.vo.trade.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;

import com.hyjf.cs.trade.bean.rtbbatch.TimesBean;
import com.hyjf.cs.trade.mq.MailProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.service.RtbLoansBatchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ${yaoy}
 * @version RtbBatchController, v0.1 2018/6/13 11:17
 */
@RestController
@RequestMapping("/batch/rtb")
public class RtbBatchController {
    private static final Logger logger = LoggerFactory.getLogger(RtbBatchController.class);

    /**
     * 任务状态:未执行
     */
    private static final Integer STATUS_WAIT = 0;

    /**
     * 任务状态:已完成
     */
    private static final Integer STATUS_SUCCESS = 1;

    /**
     * 任务状态:执行中
     */
    private static final Integer STATUS_RUNNING = 2;

    /**
     * 任务状态:错误
     */
    private static final Integer STATUS_ERROR = 9;

    /**
     * 任务执行次数
     */
    public Map<String, TimesBean> runTimes = new HashMap<String, TimesBean>();

    @Autowired
    RtbLoansBatchService rtbLoansBatchService;

    @Autowired
    private MailProducer mailProducer;

    @Value("${hyjf.online}")
    private String hyjf_online_flag;

    @RequestMapping("rtbloans")
    public void rtbInterestRepay() {

        // 取得未放款任务
        List<BorrowApicronVo> listApicron = rtbLoansBatchService.getBorrowApicronList(STATUS_WAIT, 0);
        if (!CollectionUtils.isEmpty(listApicron)) {
            // 循环进行放款
            for (BorrowApicronVo apicron : listApicron) {
                int errorCnt = 0;
                Long startTime = GetDate.getMillis();
                // 错误信息
                StringBuffer sbError = new StringBuffer();
                try {
                    logger.info("融通宝加息自动放款任务开始。[订单号：" + apicron.getBorrowNid() + "]");
                    // 更新任务API状态为进行中
                    Integer updateFlag = rtbLoansBatchService.updateBorrowApicron2(apicron.getId(), STATUS_RUNNING);
                    if(updateFlag<1)  {
                        logger.error("更新apicron失败...apicron is :{}", JSONObject.toJSON(apicron));
                        continue;
                    }
                    // 借款编号
                    String borrowNid = apicron.getBorrowNid();
                    // 借款人ID
                    Integer borrowUserId = apicron.getUserId();
                    // 取得投资详情列表
                    List<IncreaseInterestInvestVo> listTender = rtbLoansBatchService.getBorrowTenderList(borrowNid);
                    if (!CollectionUtils.isEmpty(listTender)) {
                        // 取得借款人账户信息
                        AccountVO borrowAccount = rtbLoansBatchService.getAccountByUserId(borrowUserId);
                        if (borrowAccount == null) {
                            throw new RuntimeException("借款人账户不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
                        }
                        // 借款人在银行的账户信息
                        BankOpenAccountVO borrowUserCust = rtbLoansBatchService.getBankOpenAccount(borrowUserId);
                        if (borrowUserCust == null || StringUtils.isEmpty(borrowUserCust.getAccount())) {
                            throw new RuntimeException("借款人未开户。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
                        }
                        // 取得借款详情
                        BorrowWithBLOBsVo borrow = rtbLoansBatchService.getBorrow(borrowNid);
                        if (borrow == null) {
                            throw new RuntimeException("借款详情不存在。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
                        }
                        // 投资信息
                        IncreaseInterestInvestVo borrowTender = null;
                        // 投资总件数
                        int size = listTender.size();


                        /** 循环投资详情列表 */   // todo 迁移到原子层更新
                        for (int i = 0; i < size; i++) {
                            borrowTender = listTender.get(i);
                            try {
                                if (Validator.isNull(borrowTender.getLoanOrderId())) {
                                    // 设置放款订单号
                                    borrowTender.setLoanOrderId(GetOrderIdUtils.getOrderId2(borrowTender.getUserId()));
                                    // 设置放款时间
                                    borrowTender.setOrderDate(GetOrderIdUtils.getOrderDate());
                                    // 更新放款信息
                                    rtbLoansBatchService.updateBorrowTender(borrowTender);
                                }
                                rtbLoansBatchService.updateBorrowLoans(apicron, borrowTender);
                            } catch (Exception e) {
                                sbError.append(e.getMessage()).append("<br/>");
                                logger.info("exception is :" + e);
                                errorCnt++;
                            }
                        }

                        // 有错误时
                        if (errorCnt > 0) {
                            throw new Exception("融通宝加息放款时发生错误。" + "[借款编号：" + borrowNid + "]," + "[错误件数：" + errorCnt + "]");
                        }
                    }
                    // 更新任务API状态为完成
                    rtbLoansBatchService.updateBorrowApicron2(apicron.getId(), STATUS_SUCCESS);
                    // 清除重新放款任务
                    runTimes.remove(apicron.getBorrowNid());
                } catch (Exception e) {
                    int runCnt = 1;
                    if (runTimes.containsKey(apicron.getBorrowNid())) {
                        TimesBean bean = runTimes.get(apicron.getBorrowNid());
                        bean.setCnt(bean.getCnt() + 1);
                        bean.setTime(GetDate.getMyTimeInMillis());
                        runCnt = bean.getCnt();
                        runTimes.put(apicron.getBorrowNid(), bean);
                    } else {
                        TimesBean bean = new TimesBean();
                        bean.setCnt(runCnt);
                        bean.setTime(GetDate.getMyTimeInMillis());
                        bean.setStatus(1);
                        runTimes.put(apicron.getBorrowNid(), bean);
                    }
                    logger.info("exception", e);
                    if (runCnt >= 3) {
                        // 清除重新放款任务
                        runTimes.remove(apicron.getBorrowNid());
                        if (sbError.length() == 0) {
                            sbError.append(e.getMessage());
                        }
                        // 更新任务API状态为错误
                        rtbLoansBatchService.updateBorrowApicron(apicron.getId(), STATUS_ERROR, sbError.toString());
                        logger.info("融通宝自动放款任务发生错误。[标号：" + apicron.getBorrowNid() + "]");
                    } else {
                        // 更新任务API状态为重新执行
                        rtbLoansBatchService.updateBorrowApicron2(apicron.getId(), STATUS_WAIT);
                    }
                    // 取得是否线上
                    String online = "生产环境";
                    String payUrl = hyjf_online_flag;
                    if (payUrl == null || !payUrl.contains("online")) {
                        online = "测试环境";
                    }
                    // 发送错误邮件
                    StringBuffer msg = new StringBuffer();
                    msg.append("借款标号：").append(apicron.getBorrowNid()).append("<br/>");
                    msg.append("放款时间：").append(GetDate.formatTime()).append("<br/>");
                    msg.append("执行次数：").append("第" + runCnt + "次").append("<br/>");
                    msg.append("错误信息：").append(e.getMessage()).append("<br/>");
                    msg.append("详细错误信息：<br/>").append(sbError.toString());
                    String[] toMail = new String[]{};
                    if ("测试环境".equals(online)) {
                        toMail = new String[]{"jiangying@hyjf.com", "liudandan@hyjf.com"};
                    } else {
                        toMail = new String[]{"wangkun@hyjf.com", "gaohonggang@hyjf.com",};
                    }
                    MailMessage message = new MailMessage(null, null, "[" + online + "] " + apicron.getBorrowNid() + " 第" + runCnt + "次 融通宝加息放款失败", msg.toString(), null, toMail, null,
                            "maillSendForMailingAddressMsg");
                    try {
                        mailProducer.messageSend(new Producer.MassageContent(MQConstant.MAIL_TOPIC, JSON.toJSONBytes(message)));
                    } catch (MQException e1) {
                        logger.error("融通宝定时放款邮件发送失败...", e1);
                    }

                } finally {
                    Long endTime = GetDate.getMillis();
                    logger.info("融通宝加息自动放款任务结束。[订单号：" + apicron.getBorrowNid() + "], 耗时：" + (endTime - startTime) / 1000 + "s");
                }
            }
        }


    }

    /**
     * 定时更新任务(23点,1点,3点,5点,7点)
     */
    public void autoloans() {
        // 取得放款失败任务
        List<BorrowApicronVo> listApicron = rtbLoansBatchService.getBorrowApicronList(STATUS_ERROR, 0);
        if (listApicron != null && listApicron.size() > 0) {
            for (BorrowApicronVo apicron : listApicron) {
                // 更新任务API状态为重新执行
                rtbLoansBatchService.updateBorrowApicron2(apicron.getId(), STATUS_WAIT);
            }
        }
        // 取得放款失败任务
        List<BorrowApicronVo> listApicron2 = rtbLoansBatchService.getBorrowApicronListWithRepayStatus(STATUS_ERROR, 1);
        if (listApicron2 != null && listApicron2.size() > 0) {
            for (BorrowApicronVo apicron : listApicron2) {
                // 更新任务API状态为重新执行
                rtbLoansBatchService.updateBorrowApicronOfRepayStatus(apicron.getId(), STATUS_WAIT);
            }
        }
    }
}

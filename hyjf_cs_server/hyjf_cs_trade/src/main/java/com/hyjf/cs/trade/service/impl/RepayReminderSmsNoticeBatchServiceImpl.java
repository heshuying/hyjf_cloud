/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.mq.SmsProducer;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.RepayReminderSmsNoticeBatchService;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version RepayReminderSmsNoticeBatchServiceImpl, v0.1 2018/6/22 10:56
 */
@Service
public class RepayReminderSmsNoticeBatchServiceImpl extends BaseTradeServiceImpl implements RepayReminderSmsNoticeBatchService {

    private static final Logger logger = LoggerFactory.getLogger(RepayReminderSmsNoticeBatchServiceImpl.class);

    /** 用户ID */
    private static final String VAL_USERID = "userId";
    /** 性别 */
    private static final String VAL_SEX = "val_sex";
    /** 用户名 */
    private static final String VAL_NAME = "val_name";


    @Autowired
    AmBorrowClient amBorrowClient;
    @Autowired
    AmBorrowRepayClient amBorrowRepayClient;
    @Autowired
    AmBorrowRepayPlanClient amBorrowRepayPlanClient;
    @Autowired
    AmUserClient userClient;
    @Autowired
    SmsProducer smsProducer;

    @Override
    public List<BorrowVO> selectBorrowList() {
        return this.amBorrowClient.selectBorrowList();
    }

    @Override
    public List<BorrowRepayPlanVO> selectBorrowRepayPlan(String borrowNid, Integer repaySmsReminder) {
        return this.amBorrowRepayPlanClient.selectBorrowRepayPlan(borrowNid,repaySmsReminder);
    }

    @Override
    public void sendSms(List<Map<String, String>> msgList, String temp) {
        if (msgList != null && msgList.size() > 0) {
            for (Map<String, String> msg : msgList) {
                if (Validator.isNotNull(msg.get(VAL_USERID)) && NumberUtils.isNumber(msg.get(VAL_USERID))) {
                    UserVO users = userClient.findUserById(Integer.valueOf(msg.get(VAL_USERID)));
                    if (users == null || Validator.isNull(users.getMobile()) || (users.getRecieveSms() != null && users.getRecieveSms() == 1)) {
                        return;
                    }
                    UserInfoVO userInfo = userClient.findUsersInfoById(Integer.valueOf(msg.get(VAL_USERID)));
                    if (StringUtils.isEmpty(userInfo.getTruename())) {
                        msg.put(VAL_NAME, users.getUsername());
                    } else if (userInfo.getTruename().length() > 1) {
                        msg.put(VAL_NAME, userInfo.getTruename().substring(0, 1));
                    } else {
                        msg.put(VAL_NAME, userInfo.getTruename());
                    }
                    Integer sex = userInfo.getSex();
                    if (Validator.isNotNull(sex)) {
                        if (sex.intValue() == 2) {
                            msg.put(VAL_SEX, "女士");
                        } else {
                            msg.put(VAL_SEX, "先生");
                        }
                    }
                    SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, null, MessageConstant.SMSSENDFORUSER, null,
                            temp, CustomConstants.CHANNEL_TYPE_NORMAL);
                    try {
                        smsProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC,
                                JSON.toJSONBytes(smsMessage)));
                    } catch (MQException e) {
                        logger.error("还款提醒发送消息通知失败...", e);
                    }
                }
            }
        }
    }

    /**
     * 获取BorrowRepay数据
     *
     * @param borrowNid
     * @param repaySmsReminder
     * @return
     */
    @Override
    public List<BorrowRepayVO> selectBorrowRepayList(String borrowNid, Integer repaySmsReminder) {
        return this.amBorrowRepayClient.selectBorrowRepayList(borrowNid,repaySmsReminder);
    }

    /**
     * 更新borrowRepay
     *
     * @param borrowRepayVO
     * @param i
     * @return
     */
    @Override
    public Integer updateBorrowRepay(BorrowRepayVO borrowRepayVO, int i) {
        borrowRepayVO.setRepaySmsReminder(i);
        BorrowRepay borrowRepay = new BorrowRepay();
        BeanUtils.copyProperties(borrowRepayVO,borrowRepay);
        return this.amBorrowRepayClient.updateBorrowRepay(borrowRepay);
    }

    /**
     *  短信发送后更新borrowRecoverPlan
     *
     * @param borrowRepayPlanVO
     * @param repaySmsReminder
     * @return
     */
    @Override
    public Integer updateBorrowRepayPlan(BorrowRepayPlanVO borrowRepayPlanVO, Integer repaySmsReminder) {
        borrowRepayPlanVO.setRepaySmsReminder(repaySmsReminder);
        BorrowRepayPlan borrowRepayPlan = new BorrowRepayPlan();
        BeanUtils.copyProperties(borrowRepayPlanVO,borrowRepayPlan);
        return this.amBorrowRepayPlanClient.updateBorrowRepayPlan(borrowRepayPlan);
    }

}

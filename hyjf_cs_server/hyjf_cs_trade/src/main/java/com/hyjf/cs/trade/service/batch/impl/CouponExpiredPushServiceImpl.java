/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.CouponExpiredPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author yaoy
 * @version CouponExpiredPushServiceImpl, v0.1 2018/6/19 15:36
 */
@Service
public class CouponExpiredPushServiceImpl implements CouponExpiredPushService {

    private static final Logger logger = LoggerFactory.getLogger(CouponExpiredPushServiceImpl.class);
    @Autowired
    private AmTradeClient couponUserClient;
    @Autowired
    private CommonProducer commonProducer;

    @Override
    public void sendExpiredMsgAct() throws MQException {
        logger.info("优惠券即将过期push消息提醒开始");
        //yyyy-MM-dd 的时间戳
        int nowBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf));
        int nowEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 1));

        //前一天
        int yestodayBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, -1));
        int yestodayEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf));

        // 取得体验金出借（无真实出借）的还款列表
        List<CouponUserVO> couponUsers = couponUserClient.selectCouponUser(nowBeginDate, nowEndDate);

        if (!CollectionUtils.isEmpty(couponUsers)) {
            for (CouponUserVO cUser : couponUsers) {
                CouponConfigVO config = couponUserClient.selectCouponConfig(cUser.getCouponCode());
                if (config == null ) {
                    logger.info("根据优惠券编号没有查询到优惠券配置，编号：{}", cUser.getCouponCode());
                    continue;
                }

                logger.info("即将过期的优惠券编号：{},面值：{},有效期截止日：{}", cUser.getCouponUserCode(), config.getCouponQuota(), GetDate.formatDate(Long.parseLong(cUser.getEndTime() + "000")));

//            UsersInfoExample uExample = new UsersInfoExample();
//            uExample.createCriteria().andUserIdEqualTo(cUser.getUserId());
//            List<UsersInfo> userInfo = usersInfoMapper.selectByExample(uExample);
//            if(userInfo == null || userInfo.isEmpty()){
//                LogUtil.errorLog(this.getClass().getName(), methodName, "根据用户ID没有查询到用户详情信息，用户ID：" + cUser.getUserId(), null);
//                continue;
//            }

                //发送push消息
                Map<String, String> param = new HashMap<String, String>();
                param.put("val_coupon_balance", config.getCouponType() == 1 ? config.getCouponQuota() + "元" : config.getCouponType() == 2 ? config.getCouponQuota() + "%" : config.getCouponQuota() + "元");
                param.put("val_coupon_type", config.getCouponType() == 1 ? "体验金" : config.getCouponType() == 2 ? "加息券" : "代金券");
                param.put("val_profit_deadline", GetDate.formatDate(Long.parseLong(cUser.getEndTime() + "000")));
                AppMsMessage appMsMessage = new AppMsMessage(cUser.getUserId(), param, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_COUPON_DEADLINE);
                commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(),appMsMessage));
            }
        }

        List<CouponUserVO> couponUsersExp = couponUserClient.selectCouponUser(yestodayBeginDate, yestodayEndDate);
        if (!CollectionUtils.isEmpty(couponUsersExp)) {
            for (CouponUserVO cUser : couponUsersExp) {
                CouponConfigVO config = couponUserClient.selectCouponConfig(cUser.getCouponCode());
                if (config == null ) {
                    logger.info("根据优惠券编号没有查询到优惠券配置，编号：{}", cUser.getCouponCode());
                    continue;
                }

                logger.info("昨天过期的优惠券编号：{},面值：{},有效期截止日： {}", cUser.getCouponUserCode(), config.getCouponQuota(), GetDate.formatDate(Long.parseLong(cUser.getEndTime() + "000")));

                //发送push消息
                Map<String, String> param = new HashMap<String, String>();
                param.put("val_coupon_balance", config.getCouponType() == 1 ? config.getCouponQuota() + "元" : config.getCouponType() == 2 ? config.getCouponQuota() + "%" : config.getCouponQuota() + "元");
                param.put("val_coupon_type", config.getCouponType() == 1 ? "体验金" : config.getCouponType() == 2 ? "加息券" : "代金券");
                AppMsMessage appMsMessage = new AppMsMessage(cUser.getUserId(), param, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_COUPON_INVALIDED);
                commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(),appMsMessage));
            }
        }

        logger.info("优惠券即将过期push消息提醒结束");
    }

}

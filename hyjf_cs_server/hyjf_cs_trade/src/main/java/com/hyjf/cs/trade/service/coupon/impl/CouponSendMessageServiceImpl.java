package com.hyjf.cs.trade.service.coupon.impl;

import static com.hyjf.common.util.PropertiesConstants.MGM10_ACTIVITY_ID;

import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.trade.CouponUserSearchRequest;
import com.hyjf.am.resquest.trade.InvitePrizeConfVO;
import com.hyjf.am.vo.coupon.UserCouponBean;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.VipAuthVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.util.CouponUtil;
import com.hyjf.cs.trade.client.AmMarketClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.coupon.CouponSendMessageService;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 15:06
 * @Description: CouponSendMessageServiceImpl
 */
@Service
public class CouponSendMessageServiceImpl implements CouponSendMessageService {
    private static final Logger logger = LoggerFactory.getLogger(CouponSendMessageServiceImpl.class);
    @Autowired
    private CommonProducer commonProducer;
    @Resource
    private AmUserClient amUserClient;
    @Resource
    private AmTradeClient couponConfigClient;

    @Resource
    private AmMarketClient invitePrizeConfigClient;
    @Resource
    private SystemConfig systemConfig;

    @Override
    public void insertUserCoupon(UserCouponBean paramBean) throws Exception{
		try {
			List<String> couponCodeList = new ArrayList<>();
			int couponCount = 0;
			int activityId = Integer.MIN_VALUE;
			// 用户编号
			Integer userId = paramBean.getUserId();
			// 发送优惠券类别标识
			Integer sendFlg = paramBean.getSendFlg();

			// 优惠券来源1：手动发放，2：活动发放，3：vip礼包
			int couponSource = 0;

			// sendFlg == 0 评测
			if (Validator.isNull(sendFlg) || sendFlg == 0) {
				if (!StringUtils.isBlank(paramBean.getCouponCode())) {
					couponCodeList.addAll(Arrays.asList(paramBean.getCouponCode().split(",")));

                    int sendCouponCount = paramBean.getSendCount();
					// 如果翻倍
					if (sendCouponCount > 1) {
						for (int i = 1; i < sendCouponCount; i++) {
							couponCodeList.add(paramBean.getCouponCode());
						}
					}
				}

				couponCount = this.sendConponAction(couponCodeList, userId, sendFlg, paramBean.getActivityId(),
						paramBean.getCouponSource(), paramBean.getRemark());
			}
            // sendFlg == 1
			else if (sendFlg == CouponUtil.NUM_ONE) {
				couponCodeList.add(systemConfig.getCouponCodeId());
				activityId = Integer.valueOf(systemConfig.getActivityId());
				couponSource = 2;
				// 发放优惠券
				couponCount = this.sendCouponAction(couponCodeList, userId, sendFlg, activityId, couponSource, "");
			}

            // sendFlg == 2
			else if (sendFlg == CouponUtil.NUM_TWO) {
				couponSource = 3;
				// VIP礼包发券
				int vipId = paramBean.getVipId();
				// 取得相应vip的优惠券配置信息
				List<VipAuthVO> vipAuthList = this.amUserClient.getVipAuthList(vipId);
				if (vipAuthList != null && vipAuthList.size() > 0) {
					// VipAuth vipAuth = vipAuthList.get(0);
					for (VipAuthVO vipAuth : vipAuthList) {
						// 该vip级别对应的优惠券编号
                        String sendCouponCode = vipAuth.getCouponCode();
						// 该vip级别对应的优惠券发放数量
                        int sendCouponCount = vipAuth.getCouponQuantity();
						couponCodeList = new ArrayList<String>();
						for (int i = 0; i < sendCouponCount; i++) {
							couponCodeList.add(sendCouponCode);
						}
						// 发放优惠券
						couponCount += this.sendCouponAction(couponCodeList, userId, sendFlg, activityId, couponSource,
								"");
					}

				}
			}

            // sendFlg == 7
			else if (sendFlg == CouponUtil.NUM_SEVEN) {
				couponSource = 2; // 活动发放
				activityId = Integer.valueOf(MGM10_ACTIVITY_ID);

				String groupCode = paramBean.getPrizeGroupCode();
				int sendCount = paramBean.getSendCount();
				if (sendCount < 0) {
					sendCount = 0;
				}

				if (StringUtils.isEmpty(groupCode) || userId == null) {
					logger.warn("发券参数不正确");
					return;
				}

				List<InvitePrizeConfVO> prizesList = invitePrizeConfigClient.getListByGroupCode(groupCode);
				for (int i = 0; i < sendCount; i++) {
					for (InvitePrizeConfVO prize : prizesList) {
						couponCodeList.add(prize.getCouponCode());
					}
				}
				// 发放优惠券
				couponCount += this.sendConponAction(couponCodeList, userId, sendFlg, activityId, couponSource, "");

			}

            // sendFlg == 11
			else if (sendFlg == CouponUtil.NUM_11) {
				// 注册送888元新手红包
				String[] codeArray = null;
				String codes = systemConfig.getRegisterCouponCode();
				if (StringUtils.isNotEmpty(codes)) {
					codeArray = codes.split(",");
					couponCodeList = Arrays.asList(codeArray);
				} else {
					logger.error("注册送888元新手红包代金券编号没有配置");
				}
				activityId = Integer.valueOf(systemConfig.getRegisterActivityId());
				couponSource = 2;
				if (checkSendRepeat(couponCodeList, userId, activityId)) {
					// 发放优惠券
					couponCount = this.sendCouponAction(couponCodeList, userId, sendFlg, activityId, couponSource, "");
				} else {
					logger.warn("用户已发放该活动优惠券");
					return;
				}
			}

            // sendFlg == 12 统一活动发券 ，活动发券都走这个分支
            else if (sendFlg == CouponUtil.NUM_12) {
			    logger.info("统一活动发券, paramBean is: {}", JSONObject.toJSONString(paramBean));
                couponCodeList.add(paramBean.getCouponCode());
                activityId = paramBean.getActivityId();
                couponSource = 2;
                this.sendConponAction(couponCodeList, userId, sendFlg, activityId, couponSource, "");
            }

			logger.info("发放优惠券：" + couponCount + " 张");
			if (couponCount > 0) {
				afterSendCoupon(couponCodeList, couponCount, userId);
			}
		} catch (Exception e) {
			logger.error("优惠券发放错误...", e);
			throw e;
		}
    }


    /**
     * 优惠券发放成功后通知用户
     * @param couponCodeList
     * @param couponCount
     * @param userId
     */
    private void afterSendCoupon(List<String> couponCodeList, int couponCount, int userId){
        logger.info("--------------发放优惠券push消息推送开始-----------------");
        String couponCode = couponCodeList.get(0);
        Integer couponType = null;
        CouponConfigVO couponConfigVO = couponConfigClient.selectCouponConfig(couponCode);
        if(null != couponConfigVO){
            couponType = couponConfigVO.getCouponType();
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("val_number", String.valueOf(couponCount));
        param.put("val_coupon_type",
                couponType == CouponUtil.NUM_TWO ? "加息券"
                        : couponType == CouponUtil.NUM_THREE ? "代金券" : "体验金");
        logger.info("用户id：" + userId + " 优惠券类型：" + param.get("val_coupon_type"));
        AppMsMessage appMsMessage =
                new AppMsMessage(userId, param, null, MessageConstant.APP_MS_SEND_FOR_USER,
                        CustomConstants.JYTZ_COUPON_SUCCESS);
        MessageContent messageContent = new MessageContent(MQConstant.APP_MESSAGE_TOPIC,
                UUID.randomUUID().toString(), appMsMessage);
        try {
            commonProducer.messageSend(messageContent);
        } catch (MQException e) {
            logger.error("优惠券发放成功后通知用户异常...", e);
        }
        logger.info("--------------发放优惠券push消息推送结束------------------");
    }

    /**
     * 校验是否重复发放
     *
     * @param couponCodeList
     * @param userId
     * @return
     */
    private boolean checkSendRepeat(List<String> couponCodeList, Integer userId, int activityId) {
        CouponUserSearchRequest couponUserRequest = new CouponUserSearchRequest();
        couponUserRequest.setCouponCodeList(couponCodeList);
        couponUserRequest.setUserId(userId);
        couponUserRequest.setActivityId(activityId);
        return couponConfigClient.getSendRepeat(couponUserRequest);
    }

    /**
     * 带返回发放的用户优惠券编号
     * @param couponCodeList
     * @param userId
     * @param sendFlg
     * @param activityId
     * @param couponSource
     * @return
     * @throws Exception
     */
    private int sendConponAction(List<String> couponCodeList, Integer userId, Integer sendFlg, Integer activityId,
                                 Integer couponSource,String content) throws Exception {
        int nowTime = GetDate.getNowTime10();
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        String channelName = amUserClient.getChannelNameByUserId(userId);

        int couponCount = 0;
        if (couponCodeList != null) {
            for (String couponCode : couponCodeList) {
                // 如果优惠券的发行数量已大于等于配置的发行数量，则不在发放该类别优惠券
                if (!this.checkSendNum(couponCode)) {
                    logger.info( "优惠券发行数量超出上限，不再发放！");
                    continue;
                }
                couponCount++;
                CouponUserVO couponUser = new CouponUserVO();
                couponUser.setCouponCode(couponCode);
                if (StringUtils.contains(couponCode, "PT")) {
                    // 体验金编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(1));
                } else if (StringUtils.contains(couponCode, "PJ")) {
                    // 加息券编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(2));
                } else if (StringUtils.contains(couponCode, "PD")) {
                    // 代金券编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(3));
                }
                // 优惠券组编号
                // couponUser.setCouponGroupCode(couponGroupCode);
                couponUser.setUserId(userId);
                if (Validator.isNotNull(sendFlg) && sendFlg != CouponUtil.NUM_TWO && Validator.isNotNull(activityId)) {
                    // 购买vip与活动无关
                    couponUser.setActivityId(activityId);
                }
                couponUser.setUsedFlag(CustomConstants.USER_COUPON_STATUS_UNUSED);

                // 根据优惠券编码查询优惠券
                CouponConfigVO config = couponConfigClient.selectCouponConfig(couponCode);
                if (null == config) {
                    continue;
                }

                // 加息券编号
                couponUser.setCouponUserCode(GetCode.getCouponUserCode(config.getCouponType()));

                if(config.getExpirationType() == 1){ //截止日
                    couponUser.setEndTime(config.getExpirationDate());
                }else if(config.getExpirationType() == 2){  //时长（月）
                    Date endDate = GetDate.countDate(GetDate.getDate(), 2, config.getExpirationLength());
                    couponUser.setEndTime((int)(endDate.getTime()/1000));
                }else if(config.getExpirationType() == 3){  //时长（天）
                    Date endDate = GetDate.countDate(GetDate.getDate(), 5, config.getExpirationLengthDay());
                    couponUser.setEndTime((int)(endDate.getTime()/1000));
                }

                couponUser.setCouponSource(couponSource);
                couponUser.setAddTime(nowTime);
                couponUser.setAddUser(CustomConstants.OPERATOR_AUTO_REPAY);
                couponUser.setUpdateTime(nowTime);
                couponUser.setUpdateUser(CustomConstants.OPERATOR_AUTO_REPAY);
                couponUser.setDelFlag(CustomConstants.FALG_NOR);
                couponUser.setChannel(channelName);
                couponUser.setAttribute(userInfo.getAttribute());
                couponUser.setContent(StringUtils.isEmpty(content)?"":content);
                couponConfigClient.insertCouponUser(couponUser);
            }
            logger.info("发放优惠券成功，发放张数：" + couponCount);
        }
        return couponCount;
    }

    private int sendCouponAction(List<String> couponCodeList, Integer userId, Integer sendFlg, Integer activityId,
                                Integer couponSource, String content) throws Exception {
        logger.info("用户："+userId+",执行发券逻辑开始  " + GetDate.dateToString(new Date()));
        int nowTime = GetDate.getNowTime10();

        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        if(userInfo == null){
            return 0;
        }

        String channelName = amUserClient.getChannelNameByUserId(userId);

        int couponCount = 0;
        if (couponCodeList != null && couponCodeList.size() > 0) {
            for (String couponCode : couponCodeList) {
                // 如果优惠券的发行数量已大于等于配置的发行数量，则不在发放该类别优惠券
                if (!this.checkSendNum(couponCode)) {
                    logger.info( "优惠券发行数量超出上限，不再发放！");
                    continue;
                }
                CouponUserVO couponUser = new CouponUserVO();
                couponUser.setCouponCode(couponCode);
                if (StringUtils.contains(couponCode, "PT")) {
                    // 体验金编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(1));
                } else if (StringUtils.contains(couponCode, "PJ")) {
                    // 加息券编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(2));
                } else if (StringUtils.contains(couponCode, "PD")) {
                    // 代金券编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(3));
                }
                // 优惠券组编号
                // couponUser.setCouponGroupCode(couponGroupCode);
                couponUser.setUserId(userId);
                if (Validator.isNotNull(sendFlg) && sendFlg != CouponUtil.NUM_TWO && Validator.isNotNull(activityId)) {
                    // 购买vip与活动无关
                    couponUser.setActivityId(activityId);
                }
                couponUser.setUsedFlag(CustomConstants.USER_COUPON_STATUS_UNUSED);

                // 根据优惠券编码查询优惠券
                CouponConfigVO config = couponConfigClient.selectCouponConfig(couponCode);
                if (null == config) {
                    continue;
                }

                Integer status = config.getStatus();
                if(status==null||status==1||status==3){
                    logger.info("优惠券审核未通过，无法发放！（coupon）"+couponCode);
                    continue;
                }
                // 加息券编号
                couponUser.setCouponUserCode(GetCode.getCouponUserCode(config.getCouponType()));

                if (config.getExpirationType() == 1) { // 截止日
                    couponUser.setEndTime(config.getExpirationDate());
                } else if(config.getExpirationType() == 2) { // 时长
                    couponUser.setEndTime((int) (GetDate.countDate(2, config.getExpirationLength()).getTime() / 1000));
                } else if(config.getExpirationType() == 3){
                    couponUser.setEndTime((int) (GetDate.countDate(5, config.getExpirationLengthDay()).getTime() / 1000));
                }
                couponUser.setCouponSource(couponSource);
                couponUser.setAddTime(nowTime);
                couponUser.setAddUser(CustomConstants.OPERATOR_AUTO_REPAY);
                couponUser.setUpdateTime(nowTime);
                couponUser.setUpdateUser(CustomConstants.OPERATOR_AUTO_REPAY);
                couponUser.setDelFlag(CustomConstants.FALG_NOR);
                couponUser.setChannel(channelName);
                couponUser.setAttribute(userInfo.getAttribute());
                couponUser.setContent(StringUtils.isEmpty(content)?"":content);
                couponConfigClient.insertCouponUser(couponUser);
                couponCount++;
            }
            logger.info("发放优惠券成功，发放张数：" + couponCount);
        }
        logger.info("用户："+userId+",执行发券逻辑结束  " + GetDate.dateToString(new Date()));
        return couponCount;
    }

    /**
     * 校验优惠券的已发行数量
     *
     * @return
     */
    private boolean checkSendNum(String couponCode) {
        int remain = couponConfigClient.checkCouponSendExcess(couponCode);
        return remain > 0 ? true : false;
    }
}

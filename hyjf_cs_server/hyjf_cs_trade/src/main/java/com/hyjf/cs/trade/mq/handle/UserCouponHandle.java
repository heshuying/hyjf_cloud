/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.BatchSubUserCouponBean;
import com.hyjf.cs.trade.client.CouponConfigClient;
import com.hyjf.cs.trade.client.CouponUserClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version UserCouponHandle, v0.1 2018/7/9 9:43
 */
@Component
public class UserCouponHandle {
    private static final Logger logger = LoggerFactory.getLogger(UserCouponHandle.class);

    @Autowired
    CouponUserClient couponUserClient;
    @Autowired
    CouponConfigClient couponConfigClient;

    public JSONObject batchInsertUserCoupon(Map<String,Object> map) throws Exception {

        JSONObject retResult = new JSONObject();
        try {
            List<BatchSubUserCouponBean> subeans = JSON.parseArray(map.get("usercoupons").toString(), BatchSubUserCouponBean.class);
            int totalcouponCount = 0;
            int succouponCount = 0;

            // 优惠券来源1：手动发放，2：活动发放，3：vip礼包
            int couponSource = 2;
            logger.info("批量发放优惠券开始： 预计" + subeans.size() + " 张");
            if (map.get("usercoupons") != null && subeans.size() > 0) {

                for (BatchSubUserCouponBean userCouponBean : subeans) {

                    List<String> couponCodeList = userCouponBean.getCouponCode();
                    Integer activityId = null;
                    if (StringUtils.isNotBlank(userCouponBean.getActivityId())) {
                        activityId = Integer.parseInt(userCouponBean.getActivityId().trim());
                    }
//                     String userId = userCouponBean.getUserId();
                    String userName = userCouponBean.getUserName();
                    logger.info("批量发放优惠券当前用户名：" + userName);
                    if (StringUtils.isBlank(userName)) {
                        continue;
                    }
                    UserVO user = this.getUserByUserName(userName);
                    logger.info("批量发放优惠券User：" + user);
                    if (user == null) {
                        continue;
                    }
                    if (couponCodeList == null || couponCodeList.isEmpty()) {
                        continue;
                    }

                    totalcouponCount = totalcouponCount + couponCodeList.size();
                    // 发放优惠券
                    int couponCount = this.sendConponAction(couponCodeList, String.valueOf(user.getUserId()), activityId, couponSource);
                    succouponCount = succouponCount + couponCount;
                    logger.info(user.getUserId() + " 发放优惠券：" + couponCount + " 张");
                }

            }

            retResult.put("status", 0);
            retResult.put("totalcouponCount", totalcouponCount);
            retResult.put("couponCount", succouponCount);
            logger.info("发放优惠券：" + totalcouponCount + " 张");


        } catch (Exception e) {
            logger.error("发送失败",e);
            throw e;

        }
        return retResult;

    }

    private int sendConponAction(List<String> couponCodeList, String userId, Integer activityId,
                                 int couponSource) throws Exception {

        // sendflg设置1跳过活动id不设置的逻辑
        return sendConponAction(couponCodeList, userId, 1, activityId, couponSource, "上传csv文件，批量发券");
    }

    public int sendConponAction(List<String> couponCodeList, String userId, Integer sendFlg, Integer activityId,
                                Integer couponSource, String content) throws Exception {
        logger.info("用户：" + userId + ",执行发券逻辑开始  " + GetDate.dateToString(new Date()));
        String methodName = "sendConponAction";
        int nowTime = GetDate.getNowTime10();
        // String couponGroupCode = CreateUUID.createUUID();

        UserInfoVO userInfo = this.getUsersInfoByUserId(Integer.parseInt(userId));
        if (userInfo == null) {
            return 0;
        }

        String channelName = this.getChannelNameByUserId(Integer.parseInt(userId));

        int couponCount = 0;
        if (couponCodeList != null && couponCodeList.size() > 0) {
            for (String couponCode : couponCodeList) {
                // 如果优惠券的发行数量已大于等于配置的发行数量，则不在发放该类别优惠券
                if (!this.checkSendNum(couponCode)) {
                    logger.info("优惠券发行数量超出上限，不再发放！");
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
                couponUser.setUserId(Integer.parseInt(userId));
                if (Validator.isNotNull(sendFlg) && sendFlg != 2 && Validator.isNotNull(activityId)) {
                    // 购买vip与活动无关
                    couponUser.setActivityId(activityId);
                }
                couponUser.setUsedFlag(CustomConstants.USER_COUPON_STATUS_UNUSED);

                // 根据优惠券编码查询优惠券
//                CouponConfigExample emConfig = new CouponConfigExample();
//                CouponConfigExample.Criteria caConfig = emConfig.createCriteria();
//                caConfig.andCouponCodeEqualTo(couponCode);
                CouponConfigVO config = couponConfigClient.selectCouponConfig(couponCode);
                if (config == null) {
                    continue;
                }
                Integer status = config.getStatus();
                if (status == null || status == 1 || status == 3) {
                    logger.info("优惠券审核未通过，无法发放！（coupon）" + couponCode);
                    continue;
                }
                // 加息券编号
                couponUser.setCouponUserCode(GetCode.getCouponUserCode(config.getCouponType()));

                if (config.getExpirationType() == 1) { // 截止日
                    couponUser.setEndTime(config.getExpirationDate());
                } else if (config.getExpirationType() == 2) { // 时长
                    couponUser.setEndTime((int) (GetDate.countDate(2, config.getExpirationLength()).getTime() / 1000));
                } else if (config.getExpirationType() == 3) {
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
                couponUser.setContent(StringUtils.isEmpty(content) ? "" : content);
                couponUserClient.insertCouponUser(couponUser);
                couponCount++;
            }
            logger.info("发放优惠券成功，发放张数：" + couponCount);
        }
        logger.info("用户：" + userId + ",执行发券逻辑结束  " + GetDate.dateToString(new Date()));
        return couponCount;
    }


    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    private UserVO getUserByUserName(String userName){
        if(StringUtils.isEmpty(userName)){
            return null;
        }
        UserVO userVO = couponUserClient.getUser(userName);
        return userVO;
    }

    /**
     * 根据用户ID取得用户信息
     *
     * @param userId
     * @return
     */
    public UserInfoVO getUsersInfoByUserId(Integer userId) {
        UserInfoVO userInfoVO = couponUserClient.getUserInfo(userId);
        if (userInfoVO != null) {
            return userInfoVO;
        }
        return null;
    }

    /**
     *
     * 获取用户注册时的渠道名称
     * @param userId
     * @return
     */
    public String getChannelNameByUserId(Integer userId){
        String channelName = couponUserClient.selectChannelNameByUserId(userId);
        return channelName;
    }

    /**
     * 校验优惠券的已发行数量
     *
     * @return
     */
    private boolean checkSendNum(String couponCode) {
        int remain =couponConfigClient.checkCouponSendExcess(couponCode);
        return remain > 0 ? true : false;
    }

}

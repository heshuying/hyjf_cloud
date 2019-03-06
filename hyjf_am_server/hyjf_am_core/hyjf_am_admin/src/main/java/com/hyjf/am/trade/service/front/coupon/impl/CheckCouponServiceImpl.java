/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.coupon.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.admin.BatchSubUserCouponBean;
import com.hyjf.am.trade.dao.mapper.auto.CouponConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.CouponUserMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponConfigCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.auto.CouponConfigExample;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.service.front.coupon.CheckCouponService;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.customize.ChannelCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version CheckCouponServiceImpl, v0.1 2018/7/6 16:22
 */
@Service
public class CheckCouponServiceImpl implements CheckCouponService {
    private static Logger logger = LoggerFactory.getLogger(CheckCouponServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CouponConfigMapper couponConfigMapper;
    @Autowired
    private CouponUserMapper couponUserMapper;
    @Autowired
    private CouponConfigCustomizeMapper couponConfigCustomizeMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ChannelCustomizeMapper channelCustomizeMapper;

    @Value("${release.coupon.accesskey}")
    public String SOA_COUPON_KEY;

    @Override
    public JSONObject batchInsertUserCoupon(Map<String, Object> map) throws Exception {
        String methodName = "batchInsertUserCoupon";
        JSONObject retResult = new JSONObject();
        try {

            List<BatchSubUserCouponBean> subeans = JSON.parseArray(map.get("usercoupons").toString(), BatchSubUserCouponBean.class);
            int totalcouponCount = 0;
            int succouponCount = 0;

            // 优惠券来源1：手动发放，2：活动发放，3：vip礼包
            int couponSource = 2;
            logger.info("批量发放优惠券开始： 预计" + subeans.size() + " 张");
            if (map.get("usercoupons").toString() != null && subeans.size() > 0) {

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
                    User user = this.getUserByUserName(userName);
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
            logger.error(methodName, e);
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

        UserInfo userInfo = this.getUsersInfoByUserId(Integer.parseInt(userId));
        if (userInfo == null) {
            return 0;
        }

        String channelName = this.getChannelNameByUserId(Integer.parseInt(userId));

        int couponCount = 0;
        if (couponCodeList != null && couponCodeList.size() > 0) {
            for (String couponCode : couponCodeList) {
                // 如果优惠券的发行数量已大于等于配置的发行数量，则不在发放该类别优惠券
                if (!this.checkSendNum(couponCode)) {
                    logger.info(methodName, "优惠券发行数量超出上限，不再发放！");
                    continue;
                }
                CouponUser couponUser = new CouponUser();
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
                CouponConfigExample emConfig = new CouponConfigExample();
                CouponConfigExample.Criteria caConfig = emConfig.createCriteria();
                caConfig.andCouponCodeEqualTo(couponCode);
                List<CouponConfig> configList = couponConfigMapper.selectByExample(emConfig);
                if (configList == null || configList.isEmpty()) {
                    continue;
                }
                CouponConfig config = configList.get(0);

                Integer status = config.getStatus();
                if (status == null || status == 1 || status == 3) {
                    logger.info(methodName, "优惠券审核未通过，无法发放！（coupon）" + couponCode);
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
                couponUser.setCreateTime(GetDate.getDate());
                couponUser.setCreateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_REPAY));
                couponUser.setUpdateTime(GetDate.getDate());
                couponUser.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_REPAY));
                couponUser.setDelFlag(CustomConstants.FALG_NOR);
                couponUser.setChannel(channelName);
                couponUser.setAttribute(userInfo.getAttribute());
                couponUser.setContent(StringUtils.isEmpty(content) ? "" : content);
                couponUserMapper.insertSelective(couponUser);
                couponCount++;
            }
            logger.info("发放优惠券成功，发放张数：" + couponCount);
        }
        logger.info("用户：" + userId + ",执行发券逻辑结束  " + GetDate.dateToString(new Date()));
        return couponCount;
    }

    /**
     * 获取用户注册时的渠道名称
     *
     * @param userId
     * @return
     * @author hsy
     */
    private String getChannelNameByUserId(Integer userId) {
        return channelCustomizeMapper.selectChannelNameByUserId(userId);
    }

    /**
     * 根据用户ID取得用户信息
     *
     * @param userId
     * @return
     */
    private UserInfo getUsersInfoByUserId(Integer userId) {
        if (userId != null) {
            UserInfoExample example = new UserInfoExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<UserInfo> usersInfoList = this.userInfoMapper.selectByExample(example);
            if (usersInfoList != null && usersInfoList.size() > 0) {
                return usersInfoList.get(0);
            }
        }
        return null;
    }

    /**
     * 校验优惠券的已发行数量
     *
     * @return
     */
    private boolean checkSendNum(String couponCode) {
        int remain = couponConfigCustomizeMapper.checkCouponSendExcess(couponCode);
        return remain > 0 ? true : false;
    }

    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    private User getUserByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }

        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(userName);
        List<User> userList = userMapper.selectByExample(example);

        if (userList != null && userList.size() == 1) {
            return userList.get(0);
        }

        return null;
    }
}

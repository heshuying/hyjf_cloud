package com.hyjf.cs.trade.service.coupon.impl;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListServiceImpl, v0.1 2018/6/23 14:08
 */
@Service
public class MyCouponListServiceImpl extends BaseTradeServiceImpl implements com.hyjf.cs.trade.service.coupon.MyCouponListService {
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 我的优惠券列表（已使用）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListUsed(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("1");
        return amTradeClient.selectMyCouponList(requestBean);
    }

    /**
     * 我的优惠券列表（未使用）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListUnUsed(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("0");
        return amTradeClient.selectMyCouponList(requestBean);
    }

    /**
     * 我的优惠券列表（已失效）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListInValid(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("4");
        return amTradeClient.selectMyCouponList(requestBean);
    }

    /**
     * 加载邀请页面统计数据
     * @param userId
     * @return
     */
    @Override
    public Map<String,String> selectInvitePageData(String userId){
        Map<String,String> resultMap = new HashMap<String, String>();
        Integer inviteCount = 0;
        Integer couponCount = 0;
        BigDecimal rewardTotal = BigDecimal.ZERO;

        // 累计邀请数
        MyInviteListRequest requestBeanInvite = new MyInviteListRequest();
        requestBeanInvite.setUserId(userId);
        inviteCount = amUserClient.selectMyInviteCount(requestBeanInvite);
        // 累计优惠券数
        MyCouponListRequest requestBeanCoupon = new MyCouponListRequest();
        requestBeanCoupon.setUserId(userId);
        requestBeanCoupon.setUsedFlag("0");
        couponCount = amTradeClient.selectMyCouponCount(requestBeanCoupon);
        // 累计邀请数
        MyInviteListRequest requestBeanReward = new MyInviteListRequest();
        requestBeanReward.setUserId(userId);
        rewardTotal = amTradeClient.selectMyRewardTotal(requestBeanReward);

        resultMap.put("inviteCount", String.valueOf(inviteCount));
        resultMap.put("couponCount", String.valueOf(couponCount));
        resultMap.put("rewardRecordsSum", String.valueOf(rewardTotal));
        resultMap.put("userId", userId);
        resultMap.put("inviteLink", systemConfig.webUserHost + "web/user/register?from=" + userId);
        return resultMap;
    }

    @Override
    public List<MyCouponListCustomizeVO> selectWechatCouponList(String userId,Integer useFlag) {
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag(String.valueOf(useFlag));
        return amTradeClient.selectWechatCouponList(requestBean);
    }


}

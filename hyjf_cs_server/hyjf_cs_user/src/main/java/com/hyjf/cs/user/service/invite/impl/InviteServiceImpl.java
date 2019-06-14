package com.hyjf.cs.user.service.invite.impl;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.invite.InviteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邀请及奖励
 * @author hesy
 * @version InviteServiceImpl, v0.1 2018/6/23 17:16
 */
@Service
public class InviteServiceImpl extends BaseUserServiceImpl implements InviteService {

    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 邀请列表请求校验
     */
    @Override
    public void checkForInviteList(Map<String, String> param){
        String currPage = param.get("currPage");
        String pageSize = param.get("pageSize");
        if(StringUtils.isBlank(currPage)){
            param.put("currPage", "1");
        }

        if(StringUtils.isBlank(pageSize)){
            param.put("pageSize", "10");
        }
    }

    /**
     * 我的邀请列表
     */
    @Override
    public List<MyInviteListCustomizeVO> selectMyInviteList(String userId, Integer limitStart, Integer limitEnd){
        MyInviteListRequest requestBean = new MyInviteListRequest();
        requestBean.setUserId(userId);
        requestBean.setLimitStart(limitStart);
        requestBean.setLimitEnd(limitEnd);
        List<MyInviteListCustomizeVO> result = amUserClient.selectMyInviteList(requestBean);
        if(result == null){
            return Collections.emptyList();
        }
        return result;
    }

    /**
     * 我的邀请记录总数
     */
    @Override
    public Integer selectMyInviteCount(String userId){
        MyInviteListRequest requestBean = new MyInviteListRequest();
        requestBean.setUserId(userId);
        return amUserClient.selectMyInviteCount(requestBean);
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
//        couponCount = amTradeClient.selectMyCouponCount(requestBeanCoupon);
        // 累计邀请数
        MyInviteListRequest requestBeanReward = new MyInviteListRequest();
        requestBeanReward.setUserId(userId);
        rewardTotal = amTradeClient.selectMyRewardTotal(requestBeanReward);

        resultMap.put("inviteCount", String.valueOf(inviteCount));
        resultMap.put("couponCount", String.valueOf(couponCount));
        resultMap.put("rewardRecordsSum", String.valueOf(rewardTotal));
        resultMap.put("userId", userId);
        return resultMap;
    }

    /**
     * 获取分享信息
     * @author wgx
     * @date 2019/05/09
     */
    @Override
    public ShareNewsBeanVO queryShareNews() {
        return amConfigClient.queryShareNews();
    }

}

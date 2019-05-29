package com.hyjf.cs.trade.service.reward.impl;

import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.reward.RewardService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 邀请及奖励
 * @author hesy
 * @version InviteServiceImpl, v0.1 2018/6/23 17:16
 */
@Service
public class RewardServiceImpl extends BaseTradeServiceImpl implements RewardService {

    /**
     * 奖励列表请求校验
     * @auther: hesy
     * @date: 2018/6/23
     */
    @Override
    public void checkForRewardList(Map<String, String> param){
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
     * 我的奖励列表
     */
    @Override
    public List<MyRewardRecordCustomizeVO> selectMyRewardList(String userId, Integer limitStart, Integer limitEnd){
        MyInviteListRequest requestBean = new MyInviteListRequest();
        requestBean.setUserId(userId);
        requestBean.setLimitStart(limitStart);
        requestBean.setLimitEnd(limitEnd);
        return amTradeClient.selectMyRewardList(requestBean);
    }

    /**
     * 我的奖励列表总记录数
     * @param userId
     * @return
     */
    @Override
    public Integer selectMyRewardCount(String userId){
        MyInviteListRequest requestBean = new MyInviteListRequest();
        requestBean.setUserId(userId);
        return amTradeClient.selectMyRewardCount(requestBean);
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

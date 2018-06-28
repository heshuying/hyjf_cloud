package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version RewardService, v0.1 2018/6/23 18:02
 */
public interface RewardService extends BaseTradeService{
    void checkForRewardList(Map<String, String> param);

    List<MyRewardRecordCustomizeVO> selectMyRewardList(String userId, Integer limitStart, Integer limitEnd);

    Integer selectMyRewardCount(String userId);
}

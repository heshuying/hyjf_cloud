package com.hyjf.am.trade.service;

import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 我的邀请记录
 * @author hesy
 * @version MyRewardService, v0.1 2018/6/22 20:15
 */
public interface MyRewardService {
    List<MyRewardRecordCustomizeVO> selectMyRewardList(String userId, Integer limitStart, Integer limitEnd);

    BigDecimal sumMyRewardTotal(String userId);
}

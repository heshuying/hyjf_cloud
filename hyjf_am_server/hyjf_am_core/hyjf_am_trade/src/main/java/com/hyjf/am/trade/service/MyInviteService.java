package com.hyjf.am.trade.service;

import com.hyjf.am.vo.trade.MyInviteListCustomizeVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;

import java.util.List;

/**
 * 我的邀请记录
 * @author hesy
 * @version MyInviteService, v0.1 2018/6/22 20:15
 */
public interface MyInviteService {
    List<MyInviteListCustomizeVO> selectMyInviteList(String userId, Integer limitStart, Integer limitEnd);

    List<MyRewardRecordCustomizeVO> selectMyRewardList(String userId, Integer limitStart, Integer limitEnd);
}

package com.hyjf.cs.user.service.invite;

import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version InviteService, v0.1 2018/6/23 18:02
 */
public interface InviteService extends BaseUserService {
    void checkForInviteList(Map<String, String> param);

    List<MyInviteListCustomizeVO> selectMyInviteList(String userId, Integer limitStart, Integer limitEnd);

}

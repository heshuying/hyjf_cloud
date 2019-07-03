package com.hyjf.cs.user.service.invite;

import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version InviteService, v0.1 2018/6/23 18:02
 */
public interface InviteService extends BaseUserService {
    /**
     * 邀请列表请求校验
     */
    void checkForInviteList(Map<String, String> param);
    /**
     * 我的邀请列表
     */
    List<MyInviteListCustomizeVO> selectMyInviteList(String userId, Integer limitStart, Integer limitEnd);
    /**
     * 我的邀请记录总数
     */
    Integer selectMyInviteCount(String userId);

    Map<String,String> selectInvitePageData(String userId);

    /**
     * 获取分享信息
     * @author wgx
     * @date 2019/05/09
     */
    ShareNewsBeanVO queryShareNews();
}

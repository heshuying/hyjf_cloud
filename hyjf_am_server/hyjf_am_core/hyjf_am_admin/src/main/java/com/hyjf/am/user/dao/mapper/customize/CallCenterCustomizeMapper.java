package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;

import java.util.List;

/**
 * @author libin
 * @version CallCenterCustomizeMapper, v0.1 2018/5/8 14:14
 */
public interface CallCenterCustomizeMapper {
    /**
     * 查询呼叫中心未分配客服的用户（复投用户筛选）
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * 查询呼叫中心未分配客服的用户（流失用户筛选）
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
}

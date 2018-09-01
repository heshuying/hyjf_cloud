/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.callcenter.impl;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;
import com.hyjf.am.user.service.callcenter.CallCenterAdminService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version CallCenterBankServiceImpl, v0.1 2018/6/6 14:22
 */
@Service
public class CallCenterAdminServiceImpl extends BaseServiceImpl implements CallCenterAdminService {

    /**
     * 查询呼叫中心未分配客服的用户（复投用户筛选）
     *
     * @param callCenterUserInfoRequest
     * @return
     */
    @Override
    public List<CallcenterUserBaseCustomize> getNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
        List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceFuTouUsersList(callCenterUserInfoRequest);
        return CallcenterUserBaseCustomizeList;
    }

    /**
     * 查询呼叫中心未分配客服的用户（流失用户筛选）
     *
     * @param callCenterUserInfoRequest
     * @return
     */
    @Override
    public List<CallcenterUserBaseCustomize> getNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
        List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceLiuShiUsersList(callCenterUserInfoRequest);
        return CallcenterUserBaseCustomizeList;
    }
}

package com.hyjf.am.user.service.callcenter;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * @author wangjun
 * @version UpdateBankCardBatchService, v0.1 2018/5/29 14:58
 */
public interface CallCenterAdminService extends BaseService {
    /**
     * 查询呼叫中心未分配客服的用户（复投用户筛选）
     *
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallcenterUserBaseCustomize> getNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * 查询呼叫中心未分配客服的用户（流失用户筛选）
     *
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallcenterUserBaseCustomize> getNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
}
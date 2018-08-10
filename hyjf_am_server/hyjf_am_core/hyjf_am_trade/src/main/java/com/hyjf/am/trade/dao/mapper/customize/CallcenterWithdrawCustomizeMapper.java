package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.trade.dao.model.customize.CallCenterWithdrawCustomize;

public interface CallcenterWithdrawCustomizeMapper {

    /**
     * 获取用户提现明细
     * @author wangjun
     * @param centerBaseRequest
     * @return
     */
    List<CallCenterWithdrawCustomize> getWithdrawRecordList(CallCenterBaseRequest centerBaseRequest);
}
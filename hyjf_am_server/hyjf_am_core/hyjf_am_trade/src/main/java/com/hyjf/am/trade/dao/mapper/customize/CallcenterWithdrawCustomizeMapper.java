package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.trade.dao.model.customize.CallCenterWithdrawCustomize;

import java.util.List;

public interface CallcenterWithdrawCustomizeMapper {

    /**
     * 获取用户提现明细
     * @author wangjun
     * @param centerBaseRequest
     * @return
     */
    List<CallCenterWithdrawCustomize> getWithdrawRecordList(CallCenterBaseRequest centerBaseRequest);
}
package com.hyjf.am.trade.dao.mapper.customize.callcenter;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterRechargeCustomize;

public interface CallcenterRechargeCustomizeMapper {
    /**
     * 查询充值明细
     * @author wangjun
     * @param centerBaseRequest
     * @return
     */
    public List<CallCenterRechargeCustomize> queryRechargeList(CallCenterBaseRequest centerBaseRequest);
}

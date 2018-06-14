package com.hyjf.am.trade.service.callcenter;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.resquest.callcenter.CallCenterRepaymentRequest;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterAccountDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterHtjRepaymentDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.callcenter.CallCenterHztRepaymentDetailCustomize;

import java.util.List;

/**
 * @author wangjun
 * @version CallCenterTradeService, v0.1 2018/6/11 17:52
 */
public interface CallCenterTradeService {
    /**
     * 按照用户名/手机号查询还款明细（直投产品，含承接的债权）
     * @param callCenterRepaymentRequest
     * @return List<CouponUserCustomize>
     * @author wangjun
     */
     List<CallCenterHztRepaymentDetailCustomize> getHztRepaymentDetailList(CallCenterRepaymentRequest callCenterRepaymentRequest);
    /**
     * 按照用户名/手机号查询还款明细（汇添金）
     * @param callCenterRepaymentRequest
     * @return List<CouponUserCustomize>
     * @author wangjun
     */
     List<CallCenterHtjRepaymentDetailCustomize> getHtjRepaymentDetailList(CallCenterRepaymentRequest callCenterRepaymentRequest);

    /**
     * 查询资金明细
     * @param callCenterAccountDetailRequest
     * @return List<CallCenterHtjRepaymentDetailCustomize>
     * @author wangjun
     */
     List<CallCenterAccountDetailCustomize> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest);
}

/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterHtjRepaymentDetailVO;
import com.hyjf.am.vo.callcenter.CallCenterHztRepaymentDetailVO;

import java.util.List;

/**
 * @author wangjun
 * @version AccountBankClient, v0.1 2018/6/6 13:49
 */
public interface RepaymentDetailClient {
    /**
     * 按照用户名/手机号查询还款明细（直投产品，含承接的债权）
     * @param callCenterBaseRequest
     * @return List<CallCenterHztRepaymentDetailVO>
     * @author wangjun
     */
     List<CallCenterHztRepaymentDetailVO> getHztRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest);
    /**
     * 按照用户名/手机号查询还款明细（汇添金）
     * @param callCenterBaseRequest
     * @return List<CallCenterHtjRepaymentDetailVO>
     * @author wangjun
     */
     List<CallCenterHtjRepaymentDetailVO> getHtjRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest);
}

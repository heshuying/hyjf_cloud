/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.service;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterRechargeVO;

import java.util.List;

/**
 * @author wangjun
 * @version SrchRechargeInfoService, v0.1 2018/6/14 14:38
 */
public interface SrchRechargeInfoService {
    /**
     * 查询充值明细
     * @param callCenterBaseRequest
     * @return List<CallCenterRechargeVO>
     * @author wangjun
     */
     List<CallCenterRechargeVO> queryRechargeList(CallCenterBaseRequest callCenterBaseRequest);
}

/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.service.impl;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterRechargeVO;
import com.hyjf.callcenter.client.SrchRechargeInfoClient;
import com.hyjf.callcenter.service.SrchRechargeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version SrchRechargeInfoServiceImpl, v0.1 2018/6/14 14:42
 */
@Service
public class SrchRechargeInfoServiceImpl implements SrchRechargeInfoService {
    @Autowired
    private SrchRechargeInfoClient srchRechargeInfoClient;
    /**
     * 查询充值明细
     * @author wangjun
     * @param callCenterBaseRequest
     * @return
     */
    @Override
    public List<CallCenterRechargeVO> queryRechargeList(CallCenterBaseRequest callCenterBaseRequest) {
        return srchRechargeInfoClient.queryRechargeList(callCenterBaseRequest);
    }
}

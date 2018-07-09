package com.hyjf.callcenter.service.impl;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;
import com.hyjf.callcenter.client.AmTradeClient;
import com.hyjf.callcenter.service.SrchCapitalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version SrchCapitalInfoServiceImpl, v0.1 2018/6/13 15:32
 */
@Service
public class SrchCapitalInfoServiceImpl implements SrchCapitalInfoService {
    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 查询资金明细
     * @param callCenterAccountDetailRequest
     * @return List<CallCenterAccountDetailVO>
     * @author wangjun
     */
	@Override
	public List<CallCenterAccountDetailVO> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest) {
		return amTradeClient.queryAccountDetails(callCenterAccountDetailRequest);
	}
	
}

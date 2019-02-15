package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.EvaluationCheckLogService;
import com.hyjf.admin.service.EvaluationCheckService;
import com.hyjf.am.response.trade.EvaluationCheckLogResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationCheckLogServiceImpl implements EvaluationCheckLogService {


	@Autowired
	private AmTradeClient tradeClient;
    /**
     * 获取列表
     * 
     * @return
     */
    public EvaluationCheckLogResponse getEvaluationCheckLogList(EvaluationCheckLogRequest request) {
		EvaluationCheckLogResponse evaluationConfigVOList = tradeClient.getEvaluationCheckLogList(request);
		return  evaluationConfigVOList;
    }
}

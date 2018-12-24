package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.EvaluationMoneyLogService;
import com.hyjf.am.response.trade.EvaluationMoneyLogResponse;
import com.hyjf.am.resquest.admin.EvaluationMoneyLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationMoneyLogServiceImpl implements EvaluationMoneyLogService {


	@Autowired
	private AmTradeClient tradeClient;
    /**
     * 获取列表
     * 
     * @return
     */
    public EvaluationMoneyLogResponse getEvaluationMoneyLogList(EvaluationMoneyLogRequest request) {
		EvaluationMoneyLogResponse evaluationConfigVOList = tradeClient.getEvaluationMoneyLogList(request);
		return  evaluationConfigVOList;
    }
}

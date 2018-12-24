package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.EvaluationCheckService;
import com.hyjf.admin.service.EvaluationMoneyService;
import com.hyjf.am.response.trade.EvaluationCheckResponse;
import com.hyjf.am.response.trade.EvaluationMoneyResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckRequest;
import com.hyjf.am.resquest.admin.EvaluationMoneyRequest;
import com.hyjf.am.vo.trade.EvaluationCheckConfigVO;
import com.hyjf.am.vo.trade.EvaluationMoneyConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationMoneyServiceImpl implements EvaluationMoneyService {


	@Autowired
	private AmTradeClient tradeClient;
    /**
     * 获取列表列表
     * 
     * @return
     */
    public EvaluationMoneyResponse getEvaluationMoneyList(EvaluationMoneyRequest request) {
		EvaluationMoneyResponse evaluationConfigVOList = tradeClient.getEvaluationMoneyList(request);
		return  evaluationConfigVOList;
    }


	/**
     * 根据主键获取
     * 
     * @return
     */
    public EvaluationMoneyConfigVO getEvaluationMoneyById(Integer id) {
		EvaluationMoneyConfigVO FeeConfig = tradeClient.getEvaluationMoneyById(id);
        return FeeConfig;
    }

    /**
	 * 维护更新
	 *
	 * @param record
	 */
	public EvaluationMoneyResponse updateEvaluationMoney(EvaluationMoneyRequest record) {
		return  tradeClient.updateEvaluationMoney(record);
	}
}

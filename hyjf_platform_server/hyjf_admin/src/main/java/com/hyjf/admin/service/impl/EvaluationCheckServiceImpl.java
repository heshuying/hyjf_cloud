package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.EvaluationCheckService;
import com.hyjf.am.response.trade.EvaluationCheckResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckRequest;
import com.hyjf.am.vo.admin.EvaluationCheckConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationCheckServiceImpl implements EvaluationCheckService {


	@Autowired
	private AmTradeClient tradeClient;
    /**
     * 获取列表列表
     * 
     * @return
     */
    public EvaluationCheckResponse getEvaluationCheckList(EvaluationCheckRequest request) {
		EvaluationCheckResponse evaluationConfigVOList = tradeClient.getEvaluationCheckList(request);
		return  evaluationConfigVOList;
    }


	/**
     * 根据主键获取
     * 
     * @return
     */
    public EvaluationCheckConfigVO getEvaluationCheckById(Integer id) {
		EvaluationCheckConfigVO FeeConfig = tradeClient.getEvaluationCheckById(id);
        return FeeConfig;
    }

    /**
	 * 维护更新
	 *
	 * @param record
	 */
	public EvaluationCheckResponse updateEvaluationCheck(EvaluationCheckRequest record) {
		return  tradeClient.updateEvaluationCheck(record);
	}
}

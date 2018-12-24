package com.hyjf.admin.service;

import com.hyjf.am.response.trade.EvaluationCheckResponse;
import com.hyjf.am.response.trade.EvaluationMoneyResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckRequest;
import com.hyjf.am.resquest.admin.EvaluationMoneyRequest;
import com.hyjf.am.vo.trade.EvaluationCheckConfigVO;
import com.hyjf.am.vo.trade.EvaluationMoneyConfigVO;

public interface EvaluationMoneyService {

    /**
     * 获取测评配置,开关列表
     * 
     * @return
     */
    EvaluationMoneyResponse getEvaluationMoneyList(EvaluationMoneyRequest request);

    /**
     * 获取单个测评配置
     * 
     * @return
     */
    EvaluationMoneyConfigVO getEvaluationMoneyById(Integer id);

    /**
     * 更新
     *
     * @param record
     */
    EvaluationMoneyResponse updateEvaluationMoney(EvaluationMoneyRequest record);

}
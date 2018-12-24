package com.hyjf.admin.service;

import com.hyjf.am.response.trade.EvaluationCheckResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckRequest;
import com.hyjf.am.vo.trade.EvaluationCheckConfigVO;

public interface EvaluationCheckService {

    /**
     * 获取测评配置,开关列表
     * 
     * @return
     */
    EvaluationCheckResponse getEvaluationCheckList(EvaluationCheckRequest request);

    /**
     * 获取单个测评配置
     * 
     * @return
     */
    EvaluationCheckConfigVO getEvaluationCheckById(Integer id);

    /**
     * 更新
     *
     * @param record
     */
    EvaluationCheckResponse updateEvaluationCheck(EvaluationCheckRequest record);

}